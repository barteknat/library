package com.crud.library.service;

import com.crud.library.domain.BookItem;
import com.crud.library.domain.Lend;
import com.crud.library.domain.User;
import com.crud.library.dto.LendDto;
import com.crud.library.mapper.LendMapper;
import com.crud.library.repository.BookItemRepository;
import com.crud.library.repository.LendRepository;
import com.crud.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.crud.library.status.LendStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class LendService {

    private final LendRepository repository;
    private final LendMapper mapper;
    private final BookItemRepository bookItemRepository;
    private final UserRepository userRepository;

    public LendDto borrowBookItem(long userId, long bookItemId, LendDto lendDto) {
        if (isUserOrBookItemNotExists(userId, bookItemId)) return null;
        if (isExists(bookItemId)) return null;
        BookItem bookItem = bookItemRepository.findById(bookItemId);
        if (isBorrowed(bookItem)) return null;
        User user = userRepository.findById(userId);
        Lend lend = mapper.mapToLend(lendDto);
        lend.setBookItem(bookItem);
        user.getLends().add(lend);
        lend.setUser(user);
        bookItem.setStatus(BORROWED);
        return mapper.mapToLendDto(repository.save(lend));
    }

    private boolean isUserOrBookItemNotExists(long userId, long bookItemId) {
        if (!userRepository.existsById(userId)) {
            log.error("USER NOT EXISTS");
            return true;
        }
        if (!bookItemRepository.existsById(bookItemId)) {
            log.error("BOOK ITEM NOT EXISTS");
            return true;
        }
        return false;
    }

    private boolean isExists(long bookItemId) {
        if (repository.existsByBookItemId(bookItemId)) {
            log.error("LEND ALREADY EXISTS");
            return true;
        }
        return false;
    }

    private boolean isBorrowed(BookItem bookItem) {
        if (!bookItem.getStatus().equals(AVAILABLE)) {
            log.error("BOOK ITEM IS NOT AVAILABLE");
            return true;
        }
        return false;
    }

    public void giveBackBookItem(long id, LocalDate date) {
        if (isLendExists(id)) return;
        Lend lend = repository.findById(id);
        if (isPenalty(date, lend)) return;
        lend.getBookItem().setStatus(AVAILABLE);
        log.info("BOOK ITEM HAS BEEN RETURNED");
        mapper.mapToLendDto(repository.save(lend));
    }

    private boolean isLendExists(long id) {
        if (!repository.existsById(id)) {
            log.error("LEND NOT EXISTS");
            return true;
        }
        return false;
    }

    private boolean isPenalty(LocalDate date, Lend lend) {
        if (lend.getReturnDate().isBefore(date)) {
            log.info("\n" + "USER HAS TO PAY PENALTY FEE FOR OVER KEEPING: " + "\n" +
                    "OVER KEEPING PERIOD: " + getOverKeepingPeriod(lend, date) + " DAYS" + "\n" +
                    "PENALTY FOR ONE DAY OF OVER KEEPING: 5$" + "\n" +
                    "CHARGE USER ACCOUNT FOR: -" + countPenalty(lend, date) + "$");
            return true;
        }
        return false;
    }

    private long getOverKeepingPeriod(Lend lend, LocalDate date) {
        return ChronoUnit.DAYS.between(lend.getReturnDate(), date);
    }

    private BigDecimal countPenalty(Lend lend, LocalDate date) {
        BigDecimal penaltyForOneDay = new BigDecimal(5);
        return new BigDecimal(getOverKeepingPeriod(lend, date)).multiply(penaltyForOneDay);
    }

    public void payForBookItem(long id, String payment) {
        Lend lend = repository.findById(id);
        if (isPaymentUnnecessary(lend)) return;
        if (isPaymentNotAvailable(payment)) return;
        lend.getBookItem().setStatus(AVAILABLE);
        log.info("USER HAS PAID PENALTY FOR BOOK ITEM");
        mapper.mapToLendDto(repository.save(lend));
    }

    private boolean isPaymentUnnecessary(Lend lend) {
        if (!lend.getBookItem().getStatus().equals(LOST) && !lend.getBookItem().getStatus().equals(DAMAGED)) {
            log.error("TRANSACTION NO NEEDED");
            return true;
        }
        return false;
    }

    private boolean isPaymentNotAvailable(String payment) {
        if (!payment.equals("payForBookItem")) {
            log.error("TRANSACTION FAILED");
            return true;
        }
        return false;
    }
}
