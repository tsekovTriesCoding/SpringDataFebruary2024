package org.example.bookshopsystem.services.book;

import jakarta.transaction.Transactional;
import org.example.bookshopsystem.domain.dto.BookByTitle;
import org.example.bookshopsystem.domain.entities.Book;
import org.example.bookshopsystem.domain.enums.AgeRestriction;
import org.example.bookshopsystem.domain.enums.EditionType;
import org.example.bookshopsystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
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

    @Override
    public List<Book> findAllByAgeRestriction(String ageRestriction) {
        AgeRestriction parsed = AgeRestriction.valueOf(ageRestriction.toUpperCase());

        return this.bookRepository.findAllByAgeRestriction(parsed).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByEditionTypeAndCopiesLessThan(String editionType, int copies) {
        EditionType parsed = EditionType.valueOf(editionType.toUpperCase());

        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(parsed, copies).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerPrice, BigDecimal higherPrice) {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(lowerPrice, higherPrice)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByReleaseDateNot(int year) {
        return this.bookRepository.findAllByReleaseDateNot(year).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByReleaseDateBefore(String date) {
        int[] args = Arrays.stream(date.split("-"))
                .mapToInt(Integer::parseInt)
                .toArray();

        LocalDate parsed = LocalDate.of(args[2], args[1], args[0]);

        return this.bookRepository.findAllByReleaseDateBefore(parsed).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByTitleContaining(String string) {
        return this.bookRepository.findAllByTitleContaining(string).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByAuthorLastNameStartingWith(String prefix) {
        return this.bookRepository.findAllByAuthorLastNameStartingWith(prefix).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public int findBooksCountByBookTitleLongerThan(int size) {
        return this.bookRepository.findAllByTitleLongerThan(size).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public BookByTitle findBookByTitle(String title) {
        return this.bookRepository.findBookByTitle(title).orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    public int updateAllByReleaseDateAfter(LocalDate date, int copies) {
        return this.bookRepository.updateAllByReleaseDateAfter(date, copies);
    }

    @Override
    @Transactional
    public int removeAllByCopiesLessThan(int copies) {
        return this.bookRepository.removeAllByCopiesLessThan(copies);
    }

    @Override
    public int getAllBooksCountByAuthorFullName(String firstName, String lastName) {
        return this.bookRepository.udp_get_all_books_by_author(firstName, lastName);
    }

}
