package com.crud.library.service;

import com.crud.library.dto.BookDto;
import com.crud.library.exception.AlreadyExistsExeption;
import com.crud.library.mapper.BookMapper;
import com.crud.library.repository.BookRepository;
import com.crud.library.repository.ExemplarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.crud.library.status.ExemplarStatus.AVAILABLE;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ExemplarRepository exemplarRepository;

    public long findAvailable(long bookId) {
        return exemplarRepository.countExemplarsByBook_IdAndStatus(bookId, AVAILABLE);
    }

    @Transactional
    public BookDto createBook(BookDto bookDto) throws AlreadyExistsExeption {
        if (isExists(bookDto)) throw new AlreadyExistsExeption("BOOK ALREADY EXISTS IN DATABASE");
        return bookMapper.mapToBookDto(bookRepository.save(bookMapper.mapToBook(bookDto)));
    }

    private boolean isExists(BookDto bookDto) {
        if (bookRepository.existsBySignature(bookDto.getSignature())) {
            log.error("THE BOOK IS ALREADY EXISTS");
            return true;
        }
        return false;
    }
}
