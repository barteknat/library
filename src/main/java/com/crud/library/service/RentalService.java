package com.crud.library.service;

import com.crud.library.domain.Exemplar;
import com.crud.library.domain.Rental;
import com.crud.library.domain.User;
import com.crud.library.dto.RentalDto;
import com.crud.library.mapper.RentalMapper;
import com.crud.library.repository.ExemplarRepository;
import com.crud.library.repository.RentalRepository;
import com.crud.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.crud.library.status.RentalStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository repository;
    private final RentalMapper mapper;
    private final ExemplarRepository exemplarRepository;
    private final UserRepository userRepository;

    @Transactional
    public RentalDto rentExemplar(long userId, long exemplarId, RentalDto rentalDto) {
        if (isUserOrExemplarNotExists(userId, exemplarId)) return new RentalDto();
        if (isExists(exemplarId)) return new RentalDto();
        Exemplar exemplar = exemplarRepository.findById(exemplarId);
        if (isBorrowed(exemplar)) return new RentalDto();
        User user = userRepository.findById(userId);
        Rental rental = mapper.mapToRental(rentalDto);
        rental.setExemplar(exemplar);
        user.getRentals().add(rental);
        rental.setUser(user);
        exemplar.setStatus(BORROWED);
        return mapper.mapToRentalDto(repository.save(rental));
    }

    private boolean isUserOrExemplarNotExists(long userId, long exemplarId) {
        if (!userRepository.existsById(userId)) {
            log.error("USER NOT EXISTS");
            return true;
        }
        if (!exemplarRepository.existsById(exemplarId)) {
            log.error("EXEMPLAR NOT EXISTS");
            return true;
        }
        return false;
    }

    private boolean isExists(long exemplarId) {
        if (repository.existsByExemplarId(exemplarId)) {
            log.error("RENTAL ALREADY EXISTS");
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

    @Transactional
    public void giveBackExemplar(long id, LocalDate date) {
        if (isRentalExists(id)) return;
        Rental rental = repository.findById(id);
        if (isPenalty(date, rental)) return;
        rental.getExemplar().setStatus(AVAILABLE);
        log.info("EXEMPLAR HAS BEEN RETURNED");
        mapper.mapToRentalDto(repository.save(rental));
    }

    private boolean isRentalExists(long id) {
        if (!repository.existsById(id)) {
            log.error("RENTAL NOT EXISTS");
            return true;
        }
        return false;
    }

    private boolean isPenalty(LocalDate date, Rental rental) {
        if (rental.getReturnDate().isBefore(date)) {
            log.info("\n" + "USER HAS TO PAY PENALTY FEE FOR OVER KEEPING: " + "\n" +
                    "OVER KEEPING PERIOD: " + getOverKeepingPeriod(rental, date) + " DAYS" + "\n" +
                    "PENALTY FOR ONE DAY OF OVER KEEPING: 5$" + "\n" +
                    "CHARGE USER ACCOUNT FOR: -" + countPenalty(rental, date) + "$");
            return true;
        }
        return false;
    }

    private long getOverKeepingPeriod(Rental rental, LocalDate date) {
        return ChronoUnit.DAYS.between(rental.getReturnDate(), date);
    }

    private BigDecimal countPenalty(Rental rental, LocalDate date) {
        BigDecimal penaltyForOneDay = new BigDecimal(5);
        return new BigDecimal(getOverKeepingPeriod(rental, date)).multiply(penaltyForOneDay);
    }

    @Transactional
    public void payForExemplar(long id, String charge) {
        Rental rental = repository.findById(id);
        if (isPenaltyUnnecessary(rental)) return;
        if (isPenaltyNotAvailable(charge)) return;
        rental.getExemplar().setStatus(AVAILABLE);
        log.info("USER HAS PAID PENALTY FOR EXEMPLAR");
        mapper.mapToRentalDto(repository.save(rental));
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
