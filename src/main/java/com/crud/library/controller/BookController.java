package com.crud.library.controller;

import com.crud.library.dto.BookDto;
import com.crud.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/book")
public class BookController {

    private final BookService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public BookDto create(@RequestBody BookDto bookDto) {
        return service.createBook(bookDto);
    }
}
