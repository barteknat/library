package com.crud.library.service;

import com.crud.library.domain.Book;
import com.crud.library.domain.Exemplar;
import com.crud.library.dto.ExemplarDto;
import com.crud.library.mapper.ExemplarMapper;
import com.crud.library.repository.ExemplarRepository;
import com.crud.library.repository.BookRepository;
import com.crud.library.status.ExemplarStatus;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.crud.library.status.ExemplarStatus.AVAILABLE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExemplarService {

    private final ExemplarRepository exemplarRepository;
    private final ExemplarMapper exemplarMapper;
    private final BookRepository bookRepository;

    @Transactional
    public ExemplarDto createExemplar(long bookId) throws NotFoundException {
        if (!bookRepository.existsById(bookId)) throw new NotFoundException("BOOK NOT FOUND IN DATABASE");
        Book book = bookRepository.findById(bookId);
        Exemplar exemplar = new Exemplar();
        exemplar.setStatus(AVAILABLE);
        book.getExemplars().add(exemplar);
        exemplar.setBook(book);
        return exemplarMapper.mapToExemplarDto(exemplarRepository.save(exemplar));
    }

    @Transactional
    public ExemplarDto updateExemplar(long exemplarId, ExemplarStatus exemplarStatus) {
        if (!exemplarRepository.existsById(exemplarId)) return new ExemplarDto();
        Exemplar exemplar = exemplarRepository.findById(exemplarId);
        exemplar.setStatus(exemplarStatus);
        return exemplarMapper.mapToExemplarDto(exemplarRepository.save(exemplar));
    }
}
