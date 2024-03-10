package org.example.bookshopsystem.services.book;

import org.example.bookshopsystem.domain.dto.BookByTitle;
import org.example.bookshopsystem.domain.entities.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks(List<Book> books);

    boolean isDataSeeded();

    List<Book> findAllBooksByReleaseDateAfter(LocalDate date);

    List<Book> getAllBooksBeforeYear(LocalDate date);

    List<Book> findAllByAuthorFirstNameAndLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(String ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(String editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerPrice, BigDecimal higherPrice);

    List<Book> findAllByReleaseDateNot(int year);

    List<Book> findAllByReleaseDateBefore(String date);

    List<Book> findAllByTitleContaining(String string);

    List<Book> findAllByAuthorLastNameStartingWith(String prefix);

    int findBooksCountByBookTitleLongerThan(int size);

    BookByTitle findBookByTitle(String title);

    int updateAllByReleaseDateAfter(LocalDate date, int copies);

    int removeAllByCopiesLessThan(int copies);

    int getAllBooksCountByAuthorFullName(String firstName, String lastName);
}
