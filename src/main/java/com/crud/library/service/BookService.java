package com.crud.library.service;

import com.crud.library.dto.BookDto;
import com.crud.library.mapper.BookMapper;
import com.crud.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookMapper mapper;

    public BookDto createBook(BookDto bookDto) {
        if (isExists(bookDto)) return null;
        return mapper.mapToBookDto(repository.save(mapper.mapToBook(bookDto)));
    }

    private boolean isExists(BookDto bookDto) {
        if (repository.existsBySignature(bookDto.getSignature())) {
            log.error("THE BOOK IS ALREADY EXISTS");
            return true;
        }
        return false;
    }
}
