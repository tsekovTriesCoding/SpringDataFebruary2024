package org.example.springdataautomappingobjectsexercise.repositories;

import org.example.springdataautomappingobjectsexercise.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(long id);

    Optional<Game> findByTitle(String title);
}
