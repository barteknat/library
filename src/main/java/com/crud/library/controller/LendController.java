package com.crud.library.controller;

import com.crud.library.dto.LendDto;
import com.crud.library.service.LendService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/lend")
public class LendController {

    private final LendService service;

    @PostMapping(value = "/{userId}/{bookItemId}", consumes = APPLICATION_JSON_VALUE)
    public LendDto borrow(@PathVariable long userId, @PathVariable long bookItemId, @RequestBody LendDto lendDto) {
        return service.borrowBookItem(userId, bookItemId, lendDto);
    }

    @PutMapping(value = "/{id}/{date}")
    public void giveBack(@PathVariable long id, @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        service.giveBackBookItem(id, date);
    }

    @PutMapping(value = "/payPenalty/{id}/{payment}")
    public void payPenalty(@PathVariable long id, @PathVariable String payment) {
        service.payForBookItem(id, payment);
    }
}
