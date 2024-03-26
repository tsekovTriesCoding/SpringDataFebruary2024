package softuni.exam.service;


import softuni.exam.models.entity.Book;

import java.io.IOException;

public interface BookService {

    boolean areImported();

    String readBooksFromFile() throws IOException;

	String importBooks() throws IOException;

}
