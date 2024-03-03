package org.example.bookshopsystem;

import org.example.bookshopsystem.domain.entities.Author;
import org.example.bookshopsystem.domain.entities.Book;
import org.example.bookshopsystem.services.author.AuthorService;
import org.example.bookshopsystem.services.book.BookService;
import org.example.bookshopsystem.services.seed.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final LocalDate BOOK_YEAR = LocalDate.of(2000, 12, 31);
    private final SeedService seedService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookService bookService, AuthorService authorService) {
        this.seedService = seedService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedService.seedAllData();

        this.printAllAuthorsFullNameAndBooksCount();
        this.printAllBooksByGeorgePowell();
    }

    private void printAllBooksAfterAGivenYear() {
        System.out.println(this.bookService.findAllBooksByReleaseDateAfter(BOOK_YEAR)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.joining(System.lineSeparator())));
    }

    private void printAllAuthorsFullNameWithBooksBeforeYear() {
        List<Author> allAuthorsWithBooksBeforeYear = this.authorService.
                getAllAuthorsWithAtleastOneBookBeforeAnYear(LocalDate.of(1990, 1, 1));

        System.out.println(allAuthorsWithBooksBeforeYear.stream()
                .map(Author::getAuthorFullName)
                .collect(Collectors.joining(System.lineSeparator())));
    }

    private void printAllAuthorsFullNameAndBooksCount() {
        List<Author> authors = this.authorService.findAllOrderByBooksCountDesc();

        System.out.println(authors
                .stream()
                .map(Author::getAuthorFullNameAndCountOfBooks)
                .collect(Collectors.joining(System.lineSeparator())));

    }

    private void printAllBooksByGeorgePowell() {
        this.bookService
                .findAllByAuthorFirstNameAndLastNameOrderByReleaseDateDescTitleAsc("George", "Powell")
                .forEach(b -> System.out.println(b.getBookTitleReleaseDateCopiesFormat()));
    }
}
