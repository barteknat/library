package com.crud.library.controller;

import com.crud.library.dto.ExemplarDto;
import com.crud.library.service.ExemplarService;
import com.crud.library.status.RentalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/exemplar")
public class ExemplarController {

    private final ExemplarService service;

    @GetMapping
    public long getAvailable(@RequestParam long bookId) {
        return service.findAvailable(bookId);
    }

    @PostMapping(value = "/{id}")
    public ExemplarDto create(@PathVariable long id) {
        return service.createExemplar(id);
    }

    @PutMapping(value = "/{id}")
    public ExemplarDto update(@PathVariable long id, @RequestParam RentalStatus status) {
        return service.updateExemplar(id, status);
    }
}
