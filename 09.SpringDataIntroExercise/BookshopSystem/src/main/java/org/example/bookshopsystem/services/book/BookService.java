package org.example.bookshopsystem.services.book;

import org.example.bookshopsystem.domain.entities.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks(List<Book> books);

    boolean isDataSeeded();

    List<Book> findAllBooksByReleaseDateAfter(LocalDate date);

    List<Book> getAllBooksBeforeYear(LocalDate date);

    List<Book> findAllByAuthorFirstNameAndLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);
}
