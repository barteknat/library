package com.crud.library.repository;

import com.crud.library.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    Rental findByUser_IdAndExemplar_IdOrderByRentDateDesc(long userId, long exemplarId);

    Rental findByExemplar_IdOrderByRentDateDesc(long exemplarId);

    boolean existsByUser_IdAndExemplar_Id(long userId, long exemplarId);
}
