package com.crud.library.repository;

import com.crud.library.domain.Lend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface LendRepository extends JpaRepository<Lend, Long> {

    Lend findById(long id);

    void deleteById(long id);

    boolean existsById(long id);

    boolean existsByBookItemId(long id);
}
