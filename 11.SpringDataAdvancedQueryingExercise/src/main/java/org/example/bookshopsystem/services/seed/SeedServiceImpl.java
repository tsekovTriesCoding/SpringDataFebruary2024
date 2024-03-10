package org.example.bookshopsystem.services.seed;

import org.example.bookshopsystem.domain.entities.Author;
import org.example.bookshopsystem.domain.entities.Book;
import org.example.bookshopsystem.domain.entities.Category;
import org.example.bookshopsystem.domain.enums.AgeRestriction;
import org.example.bookshopsystem.domain.enums.EditionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.bookshopsystem.services.author.AuthorService;
import org.example.bookshopsystem.services.book.BookService;
import org.example.bookshopsystem.services.category.CategoryService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.bookshopsystem.constants.FilePath.*;

@Service
public class SeedServiceImpl implements SeedService {
    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public SeedServiceImpl(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (!this.authorService.isDataSeeded()) {

            this.authorService.seedAuthors(Files.readAllLines(Path.of(RESOURCE_URL + AUTHOR_FILE_NAME))
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(s -> Author.Builder.newInstance()
                            .setFirstName(s.split("\\s+")[0])
                            .setLastName(s.split("\\s+")[1])
                            .build())
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookService.isDataSeeded()) return;

        final List<Book> books = Files.readAllLines(Path.of(RESOURCE_URL + BOOK_FILE_NAME))
                .stream()
                .filter(s -> !s.isBlank())
                .map(row -> {
                    String[] args = row.split("\\s+");
                    String title = Arrays.stream(args)
                            .skip(5)
                            .collect(Collectors.joining(" "));

                    return Book.Builder.newInstance()
                            .setAuthor(this.authorService.getRandomAuthor())
                            .setCategories(this.categoryService.getRandomCategories())
                            .setTitle(title)
                            .setEditionType(EditionType.values()[Integer.parseInt(args[0])])
                            .setAgeRestriction(AgeRestriction.values()[Integer.parseInt(args[4])])
                            .setReleaseDate(LocalDate.parse(args[1], DateTimeFormatter.ofPattern("d/M/yyyy")))
                            .setCopies(Integer.parseInt(args[2]))
                            .setPrice(new BigDecimal(args[3]))
                            .build();
                }).toList();

        this.bookService.seedBooks(books);
    }

    @Override
    public void seedCategories() throws IOException {
        if (!this.categoryService.isDataSeeded()) {
            this.categoryService.seedCategories(Files.readAllLines(Path.of(RESOURCE_URL + CATEGORY_FILE_NAME))
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(name -> Category.Builder.newInstance()
                            .setName(name)
                            .build())
                    .collect(Collectors.toList()));
        }

    }
}
