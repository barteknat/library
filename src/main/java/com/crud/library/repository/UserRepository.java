package com.crud.library.repository;

import com.crud.library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    boolean existsById(long id);

    boolean existsByMailAddress(String mailAddress);
}
