package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softuni.exam.models.dto.BookImportDto;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_BOOK;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_BOOK;
import static softuni.exam.constants.Paths.BOOKS_PATH;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           Gson gson,
                           ValidationUtils validationUtils,
                           ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(BOOKS_PATH));
    }

    @Override
    public String importBooks() throws IOException {
        BookImportDto[] bookImportDtos = this.gson.fromJson(this.readBooksFromFile(), BookImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (BookImportDto bookImportDto : bookImportDtos) {
            boolean isValid = this.validationUtils.isValid(bookImportDto);

            if (this.bookRepository.findByTitle(bookImportDto.getTitle()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Book book = this.modelMapper.map(bookImportDto, Book.class);

                this.bookRepository.saveAndFlush(book);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_BOOK, book.getAuthor(), book.getTitle()))
                        .append(System.lineSeparator());

            } else {
                sb.append(INVALID_BOOK).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

}
