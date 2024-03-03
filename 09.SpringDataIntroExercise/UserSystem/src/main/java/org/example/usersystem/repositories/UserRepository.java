package org.example.usersystem.repositories;

import org.example.usersystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByEmailEndingWith(String sufix);
    List<User> findAllByLastTimeLoggedInBefore(LocalDateTime date);

}
