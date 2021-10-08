package com.crud.library.repository;

import com.crud.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsById(long id);

    Book findById(long id);

    boolean existsBySignature(String signature);
}
