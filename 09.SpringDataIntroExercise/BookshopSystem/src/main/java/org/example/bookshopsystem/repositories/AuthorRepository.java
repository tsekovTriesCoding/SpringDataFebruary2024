package org.example.bookshopsystem.repositories;

import org.example.bookshopsystem.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("Select a from Author a order by size(a.books) desc")
    Optional<List<Author>> findAllOrderByBooksCountDesc();

}

