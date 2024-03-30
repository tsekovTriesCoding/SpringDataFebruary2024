package org.example.mvcproject.repository;

import org.example.mvcproject.models.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByName(String name);

    Optional<List<Project>> findAllByIsFinishedIsTrue();
}
