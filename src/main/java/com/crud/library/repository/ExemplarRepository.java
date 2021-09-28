package com.crud.library.repository;

import com.crud.library.domain.Exemplar;
import com.crud.library.status.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {

    Exemplar findById(long id);

    long countExemplarsByBook_IdAndStatus(long id, RentalStatus status);

    boolean existsById(long id);
}
