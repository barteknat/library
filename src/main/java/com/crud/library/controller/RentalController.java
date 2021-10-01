package com.crud.library.controller;

import com.crud.library.dto.RentalDto;
import com.crud.library.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rental")
public class RentalController {

    private final RentalService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public RentalDto rent(@RequestParam long userId, @RequestParam long exemplarId, @RequestBody RentalDto rentalDto) {
        return service.rentExemplar(userId, exemplarId, rentalDto);
    }

    @PutMapping(value = "/{id}")
    public void giveBack(@PathVariable long id) {
        service.giveBackExemplar(id);
    }

    @PutMapping(value = "/payPenalty/{id}")
    public void payPenalty(@PathVariable long id, @RequestParam String charge) {
        service.payForExemplar(id, charge);
    }
}
