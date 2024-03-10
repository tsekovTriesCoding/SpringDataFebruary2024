package org.example.bookshopsystem;

import org.example.bookshopsystem.services.author.AuthorService;
import org.example.bookshopsystem.services.book.BookService;
import org.example.bookshopsystem.services.seed.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

        // 1. Books Titles by Age Restriction
//        String ageRestriction = scanner.nextLine();
//
//        this.bookService.findAllByAgeRestriction(ageRestriction)
//                .forEach(b -> System.out.println(b.getTitle()));

        // 2. Golden Books
//        this.bookService.findAllByEditionTypeAndCopiesLessThan("gold", 5000)
//                .forEach(b -> System.out.println(b.getTitle()));

        // 3. Books by Price
//        this.bookService.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5L), BigDecimal.valueOf(40L))
//                .stream()
//                .map(Book::getBookTitleAndPriceFormat)
//                .forEach(System.out::println);

        //
        // 4. Not Released Books
//        int year = Integer.parseInt(scanner.nextLine());
//
//        this.bookService.findAllByReleaseDateNot(year)
//                .forEach(b -> System.out.println(b.getTitle()));


        //5. Books Released Before Dat
//        String date = scanner.nextLine();
//        this.bookService.findAllByReleaseDateBefore(date)
//                .stream()
//                .map(Book::getBookTitleEditionTypeAndPriceFormat)
//                .forEach(System.out::println);

        // 6. Authors Search
//        String sufix = scanner.nextLine();
//        this.authorService.findAllByFirstNameEndingWith(sufix)
//                .stream()
//                .map(Author::getAuthorFullName)
//                .forEach(System.out::println);

        // 7. Books Search
//        String string = scanner.nextLine();
//        this.bookService.findAllByTitleContaining(string)
//                .forEach(b -> System.out.println(b.getTitle()));

        // 8. Book Titles Search
//        String prefix = scanner.nextLine();
//        this.bookService.findAllByAuthorLastNameStartingWith(prefix)
//                .stream()
//                .map(Book::getBookTitleAndAuthorFullNameFormat)
//                .forEach(System.out::println);

        // 9. Count Books
//        int size = Integer.parseInt(scanner.nextLine());
//
//        System.out.println(this.bookService.findBooksCountByBookTitleLongerThan(size));

        // 10. Total Book Copies
//        this.authorService.findAllByBookCopiesOrderByTotalCopiesDesc()
//                .stream()
//                .map(AuthorWithTotalCopiesCount::toString)
//                .forEach(System.out::println);

        // 11. Reduced Book
//        String title = scanner.nextLine();
//        System.out.println(this.bookService.findBookByTitle(title).toString());

        // 12. * Increase Book Copies
//        String date = scanner.nextLine().replaceAll(" ", "-");
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
//
//        int copies = Integer.parseInt(scanner.nextLine());
//
//        LocalDate parsed = LocalDate.parse(date, dateTimeFormatter);
//
//        int updatedBooksCount = this.bookService.updateAllByReleaseDateAfter(parsed, copies);
//
//        int totalAddedBooks = updatedBooksCount * copies;
//
//        System.out.println(totalAddedBooks);

        // 13. * Remove Books
//        int removedBooksCount = this.bookService.removeAllByCopiesLessThan(600);
//
//        System.out.println(removedBooksCount);

        // 14. * Stored Procedure
        String[] name = scanner.nextLine().split("\\s+");

        int count = this.bookService.getAllBooksCountByAuthorFullName(name[0], name[1]);

        System.out.println(count);

    }

}
