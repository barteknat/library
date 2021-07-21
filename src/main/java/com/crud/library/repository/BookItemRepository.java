package com.crud.library.repository;

import com.crud.library.domain.BookItem;
import com.crud.library.status.LendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BookItemRepository extends JpaRepository<BookItem, Long> {

    BookItem findById(long id);

    long countBookItemByBook_IdAndStatus(long id, LendStatus status);

    boolean existsById(long id);
}
