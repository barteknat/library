package com.crud.library.controller;

import com.crud.library.dto.ExemplarDto;
import com.crud.library.service.ExemplarService;
import com.crud.library.status.ExemplarStatus;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/exemplar")
public class ExemplarController {

    private final ExemplarService exemplarService;

    @PostMapping(value = "/{bookId}")
    public ExemplarDto create(@PathVariable long bookId) throws NotFoundException {
        return exemplarService.createExemplar(bookId);
    }

    @PutMapping(value = "/{exemplarId}")
    public ExemplarDto update(@PathVariable long exemplarId, @RequestParam ExemplarStatus exemplarStatus) {
        return exemplarService.updateExemplar(exemplarId, exemplarStatus);
    }
}
