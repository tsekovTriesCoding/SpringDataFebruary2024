package org.example.bookshopsystem.repositories;

import org.example.bookshopsystem.domain.dto.AuthorWithTotalCopiesCount;
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

    Optional<List<Author>> findAllByFirstNameEndingWith(String sufix);

    @Query("select new org.example.bookshopsystem.domain.dto.AuthorWithTotalCopiesCount(a.firstName, a.lastName, sum(b.copies) ) " +
            "from Author as a join a.books as b group by a.id order by sum(b.copies) desc")
    Optional<List<AuthorWithTotalCopiesCount>> findAllByBookCopiesOrderByBookCountDesc();

}

