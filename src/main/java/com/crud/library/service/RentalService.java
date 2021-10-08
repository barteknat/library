package com.crud.library.service;

import com.crud.library.domain.Exemplar;
import com.crud.library.domain.Rental;
import com.crud.library.domain.User;
import com.crud.library.dto.RentalDto;
import com.crud.library.exception.ExemplarIsBorrowedException;
import com.crud.library.exception.ExemplarPenaltyException;
import com.crud.library.exception.WrongStatusException;
import com.crud.library.mapper.RentalMapper;
import com.crud.library.repository.ExemplarRepository;
import com.crud.library.repository.RentalRepository;
import com.crud.library.repository.UserRepository;
import com.crud.library.status.ExemplarStatus;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.crud.library.status.ExemplarStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final ExemplarRepository exemplarRepository;
    private final UserRepository userRepository;

    @Transactional
    public RentalDto rentExemplar(long userId, long exemplarId) throws NotFoundException, ExemplarIsBorrowedException {
        if (isUserNotExist(userId)) throw new NotFoundException("NOT FOUND IN DATABASE");
        if (isExemplarNotExist(exemplarId)) throw new NotFoundException("NOT FOUND IN DATABASE");
        Exemplar exemplar = exemplarRepository.findById(exemplarId);
        if (isBorrowed(exemplar)) throw new ExemplarIsBorrowedException("EXEMPLAR IS ALREADY BORROWED");
        exemplar.setStatus(BORROWED);
        User user = userRepository.findById(userId);
        Rental rental = new Rental();
        rental.setRentDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now().plusDays(30));
        rental.setExemplar(exemplar);
        rental.setUser(user);
        return rentalMapper.mapToRentalDto(rentalRepository.save(rental));
    }

    @Transactional
    public void giveBackExemplar(long userId, long exemplarId, ExemplarStatus exemplarStatus) throws NotFoundException, WrongStatusException, ExemplarPenaltyException {
        if (isUserNotExist(userId)) throw new NotFoundException("USER NOT FOUND IN DATABASE");
        if (isExemplarNotExist(exemplarId)) throw new NotFoundException("EXEMPLAR NOT FOUND IN DATABASE");
        if (isRentalNotExist(userId, exemplarId)) throw new NotFoundException("RENTAL NOT FOUND IN DATABASE");
        Rental rental = rentalRepository.findByUser_IdAndExemplar_IdOrderByRentDateDesc(userId, exemplarId);
        if (isExemplarStatusNotGood(rental, exemplarStatus)) return;
        if (isBookKeepingPenalty(rental, LocalDate.now())) return;
        rental.getExemplar().setStatus(AVAILABLE);
        rentalMapper.mapToRentalDto(rentalRepository.save(rental));
        log.info("EXEMPLAR HAS BEEN RETURNED");
    }

    @Transactional
    public void payForExemplar(long exemplarId, String charge) {
        Rental rental = rentalRepository.findByExemplar_IdOrderByRentDateDesc(exemplarId);
        if (isPenaltyUnnecessary(rental)) return;
        if (isPenaltyNotAvailable(charge)) return;
        rental.getExemplar().setStatus(AVAILABLE);
        log.info("USER HAS PAID PENALTY FOR EXEMPLAR");
        rentalMapper.mapToRentalDto(rentalRepository.save(rental));
    }

    private boolean isUserNotExist(long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("USER NOT EXISTS");
            return true;
        }
        return false;
    }

    private boolean isExemplarNotExist(long exemplarId) {
        if (!exemplarRepository.existsById(exemplarId)) {
            log.error("EXEMPLAR NOT EXISTS");
            return true;
        }
        return false;
    }

    private boolean isBorrowed(Exemplar exemplar) {
        if (!exemplar.getStatus().equals(AVAILABLE)) {
            log.error("EXEMPLAR IS NOT AVAILABLE");
            return true;
        }
        return false;
    }

    private boolean isRentalNotExist(long userId, long exemplarId) {
        if (!rentalRepository.existsByUser_IdAndExemplar_Id(userId, exemplarId)) {
            log.error("RENTAL NOT EXISTS");
            return true;
        }
        return false;
    }

    private boolean isExemplarStatusNotGood(Rental rental, ExemplarStatus exemplarStatus) throws WrongStatusException, ExemplarPenaltyException {
        if (!exemplarStatus.equals(GOOD) && !exemplarStatus.equals(DAMAGED) && !exemplarStatus.equals(LOST)) throw new WrongStatusException("WRONG STATUS");
        if (exemplarStatus.equals(GOOD)) return false;
        if (exemplarStatus.equals(DAMAGED)) {
            rental.getExemplar().setStatus(DAMAGED);
            throw new ExemplarPenaltyException("USER HAS TO PAY FOR EXEMPLAR FIRST");
        }
        rental.getExemplar().setStatus(LOST);
        throw new ExemplarPenaltyException("USER HAS TO PAY FOR EXEMPLAR FIRST");
    }

    private boolean isBookKeepingPenalty(Rental rental, LocalDate dateOfReturn) {
        if (rental.getReturnDate().isBefore(dateOfReturn)) {
            log.info("\n" + "USER HAS TO PAY PENALTY FEE FOR OVER KEEPING: " + "\n" +
                    "OVER KEEPING PERIOD: " + getBookKeepingPeriod(rental, dateOfReturn) + " DAYS" + "\n" +
                    "PENALTY FOR ONE DAY OF OVER KEEPING: 5$" + "\n" +
                    "CHARGE USER ACCOUNT FOR: -" + countPenalty(rental, dateOfReturn) + "$");
            return true;
        }
        return false;
    }

    private long getBookKeepingPeriod(Rental rental, LocalDate dateOfReturn) {
        return ChronoUnit.DAYS.between(rental.getReturnDate(), dateOfReturn);
    }

    private BigDecimal countPenalty(Rental rental, LocalDate dateOfReturn) {
        BigDecimal penaltyForOneDay = new BigDecimal(5);
        return new BigDecimal(getBookKeepingPeriod(rental, dateOfReturn)).multiply(penaltyForOneDay);
    }

    private boolean isPenaltyUnnecessary(Rental rental) {
        if (!rental.getExemplar().getStatus().equals(LOST) && !rental.getExemplar().getStatus().equals(DAMAGED)) {
            log.error("TRANSACTION NO NEEDED");
            return true;
        }
        return false;
    }

    private boolean isPenaltyNotAvailable(String charge) {
        if (!charge.equals("payForExemplar")) {
            log.error("TRANSACTION FAILED");
            return true;
        }
        return false;
    }
}
