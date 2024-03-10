package org.example.bookshopsystem.services.author;

import org.example.bookshopsystem.domain.dto.AuthorWithTotalCopiesCount;
import org.example.bookshopsystem.domain.entities.Author;
import org.example.bookshopsystem.domain.entities.Book;
import org.example.bookshopsystem.repositories.AuthorRepository;
import org.example.bookshopsystem.services.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookService bookService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    @Override
    public void seedAuthors(List<Author> authors) {
        this.authorRepository.saveAllAndFlush(authors);
    }

    @Override
    public boolean isDataSeeded() {
        return this.authorRepository.count() > 0;
    }

    @Override
    public Author getRandomAuthor() {
        final long count = this.authorRepository.count();

        if (count != 0) {
            long randomId = new Random().nextLong(1L, count) + 1L;
            return this.authorRepository.findById(randomId).orElseThrow(NoSuchElementException::new);
        }

        throw new RuntimeException();
    }

    @Override
    public List<Author> getAllAuthorsWithAtleastOneBookBeforeAnYear(LocalDate date) {

        return this.bookService.getAllBooksBeforeYear(date)
                .stream()
                .map(Book::getAuthor)
                .toList();
    }

    @Override
    public List<Author> findAllOrderByBooksCountDesc() {
        return this.authorRepository.findAllOrderByBooksCountDesc().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Author> findAllByFirstNameEndingWith(String sufix) {
        return this.authorRepository.findAllByFirstNameEndingWith(sufix).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<AuthorWithTotalCopiesCount> findAllByBookCopiesOrderByTotalCopiesDesc() {
        return this.authorRepository.findAllByBookCopiesOrderByBookCountDesc().orElseThrow(NoSuchElementException::new);
    }

}
