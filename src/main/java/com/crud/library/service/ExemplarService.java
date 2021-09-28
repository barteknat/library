package com.crud.library.service;

import com.crud.library.domain.Book;
import com.crud.library.domain.Exemplar;
import com.crud.library.dto.ExemplarDto;
import com.crud.library.mapper.ExemplarMapper;
import com.crud.library.repository.ExemplarRepository;
import com.crud.library.repository.BookRepository;
import com.crud.library.status.RentalStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.crud.library.status.RentalStatus.AVAILABLE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExemplarService {

    private final ExemplarRepository repository;
    private final ExemplarMapper mapper;
    private final BookRepository bookRepository;

    public long findAvailable(long id) {
        return repository.countExemplarsByBook_IdAndStatus(id, AVAILABLE);
    }

    @Transactional
    public ExemplarDto createExemplar(long id) {
        if (!bookRepository.existsById(id)) return new ExemplarDto();
        Book book = bookRepository.findById(id);
        Exemplar exemplar = new Exemplar();
        exemplar.setStatus(AVAILABLE);
        book.getExemplars().add(exemplar);
        exemplar.setBook(book);
        return mapper.mapToExemplarDto(repository.save(exemplar));
    }

    @Transactional
    public ExemplarDto updateExemplar(long id, RentalStatus status) {
        if (!repository.existsById(id)) return new ExemplarDto();
        Exemplar exemplar = repository.findById(id);
        exemplar.setStatus(status);
        return mapper.mapToExemplarDto(repository.save(exemplar));
    }
}
