package com.crud.library.service;

import com.crud.library.domain.Book;
import com.crud.library.domain.BookItem;
import com.crud.library.dto.BookItemDto;
import com.crud.library.mapper.BookItemMapper;
import com.crud.library.repository.BookItemRepository;
import com.crud.library.repository.BookRepository;
import com.crud.library.status.LendStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.crud.library.status.LendStatus.AVAILABLE;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookItemService {

    private final BookItemRepository repository;
    private final BookItemMapper mapper;
    private final BookRepository bookRepository;

    public long findAvailable(long id) {
        return repository.countBookItemByBook_IdAndStatus(id, AVAILABLE);
    }

    public BookItemDto createBookItem(long id) {
        if (!bookRepository.existsById(id)) return null;
        Book book = bookRepository.findById(id);
        BookItem bookItem = new BookItem();
        bookItem.setStatus(AVAILABLE);
        book.getBookItems().add(bookItem);
        bookItem.setBook(book);
        return mapper.mapToBookItemDto(repository.save(bookItem));
    }

    public BookItemDto updateBookItem(long id, LendStatus status) {
        if (!repository.existsById(id)) return null;
        BookItem bookItem = repository.findById(id);
        bookItem.setStatus(status);
        return mapper.mapToBookItemDto(repository.save(bookItem));
    }
}
