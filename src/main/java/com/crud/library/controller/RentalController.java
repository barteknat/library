package com.crud.library.controller;

import com.crud.library.dto.RentalDto;
import com.crud.library.exception.ExemplarIsBorrowedException;
import com.crud.library.service.RentalService;
import com.crud.library.status.ExemplarStatus;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rental")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public RentalDto rent(@RequestParam long userId, @RequestParam long exemplarId) throws NotFoundException, ExemplarIsBorrowedException {
        return rentalService.rentExemplar(userId, exemplarId);
    }

    @PutMapping
    public void giveBack(@RequestParam long userId, @RequestParam long exemplarId, @RequestParam ExemplarStatus exemplarStatus) throws NotFoundException {
        rentalService.giveBackExemplar(userId, exemplarId, exemplarStatus);
    }

    @PutMapping(value = "/payPenalty/{rentalId}")
    public void payPenalty(@PathVariable long exemplarId, @RequestParam String charge) {
        rentalService.payForExemplar(exemplarId, charge);
    }
}
