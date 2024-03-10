package org.example.bookshopsystem.services.author;

import org.example.bookshopsystem.domain.dto.AuthorWithTotalCopiesCount;
import org.example.bookshopsystem.domain.entities.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorService {
    void seedAuthors(List<Author> authors);

    boolean isDataSeeded();

    Author getRandomAuthor();

    List<Author> getAllAuthorsWithAtleastOneBookBeforeAnYear(LocalDate date);

    List<Author> findAllOrderByBooksCountDesc();

    List<Author> findAllByFirstNameEndingWith(String sufix);

    List<AuthorWithTotalCopiesCount> findAllByBookCopiesOrderByTotalCopiesDesc();

}
