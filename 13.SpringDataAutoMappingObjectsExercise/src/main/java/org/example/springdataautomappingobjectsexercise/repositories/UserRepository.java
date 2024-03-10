package org.example.springdataautomappingobjectsexercise.repositories;

import org.example.springdataautomappingobjectsexercise.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);

    Optional<User> findByEmail(String email);
}
