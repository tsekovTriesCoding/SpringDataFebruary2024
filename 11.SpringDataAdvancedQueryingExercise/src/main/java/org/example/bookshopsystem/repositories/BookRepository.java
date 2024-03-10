package org.example.bookshopsystem.repositories;

import org.example.bookshopsystem.domain.dto.BookByTitle;
import org.example.bookshopsystem.domain.entities.Book;
import org.example.bookshopsystem.domain.enums.AgeRestriction;
import org.example.bookshopsystem.domain.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<List<Book>> findBooksByReleaseDateAfter(LocalDate date);

    Optional<List<Book>> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    Optional<List<Book>> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Optional<List<Book>> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    Optional<List<Book>> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerPrice, BigDecimal higherPrice);

    @Query("select b from Book as b where year(b.releaseDate) != :year ")
    Optional<List<Book>> findAllByReleaseDateNot(int year);

    Optional<List<Book>> findAllByTitleContaining(String string);

    Optional<List<Book>> findAllByAuthorLastNameStartingWith(String prefix);

    @Query("select count(b) from Book as b where length(b.title) > :size")
    Optional<Integer> findAllByTitleLongerThan(int size);

    @Query("select new org.example.bookshopsystem.domain.dto.BookByTitle(b.title, b.editionType, b.ageRestriction, b.price) " +
            "from Book as b where b.title = :title")
    Optional<BookByTitle> findBookByTitle(String title);

    @Query("update Book as b " +
            "set b.copies = b.copies + :copies " +
            "where b.releaseDate > :date")
    @Modifying
    int updateAllByReleaseDateAfter(LocalDate date, int copies);

    int removeAllByCopiesLessThan(int copies);

    @Query(value = "CALL udp_get_all_books_by_author(:firstName, :lastName);", nativeQuery = true)
    int udp_get_all_books_by_author(String firstName, String lastName);

}
