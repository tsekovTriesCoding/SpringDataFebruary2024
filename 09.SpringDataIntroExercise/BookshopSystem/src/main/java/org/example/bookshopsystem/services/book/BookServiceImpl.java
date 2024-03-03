package org.example.bookshopsystem.services.book;

import org.example.bookshopsystem.domain.entities.Book;
import org.example.bookshopsystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void seedBooks(List<Book> books) {
        this.bookRepository.saveAllAndFlush(books);
    }

    @Override
    public boolean isDataSeeded() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public List<Book> findAllBooksByReleaseDateAfter(LocalDate date) {
        return this.bookRepository.findBooksByReleaseDateAfter(date).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> getAllBooksBeforeYear(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateBefore(date).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByAuthorFirstNameAndLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName) {
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(firstName, lastName);
    }
}
