package com.crud.library.controller;

import com.crud.library.dto.BookDto;
import com.crud.library.exception.AlreadyExistsExeption;
import com.crud.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/book")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public long getAvailable(@RequestParam long bookId) {
        return bookService.findAvailable(bookId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public BookDto create(@RequestBody BookDto bookDto) throws AlreadyExistsExeption {
        return bookService.createBook(bookDto);
    }
}
