package com.crud.library.controller;

import com.crud.library.dto.BookItemDto;
import com.crud.library.service.BookItemService;
import com.crud.library.status.LendStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/bookItem")
public class BookItemController {

    private final BookItemService service;

    @GetMapping(value = "/{id}")
    public long getAvailable(@PathVariable long id) {
        return service.findAvailable(id);
    }

    @PostMapping(value = "/{id}")
    public BookItemDto create(@PathVariable long id) {
        return service.createBookItem(id);
    }

    @PutMapping(value = "/{id}/{status}")
    public BookItemDto update(@PathVariable long id, @PathVariable LendStatus status) {
        return service.updateBookItem(id, status);
    }
}
