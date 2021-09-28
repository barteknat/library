package com.crud.library.repository;

import com.crud.library.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    Rental findById(long id);

    void deleteById(long id);

    boolean existsById(long id);

    boolean existsByExemplarId(long id);
}
