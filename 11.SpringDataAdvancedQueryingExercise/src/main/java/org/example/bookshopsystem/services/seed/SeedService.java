package org.example.bookshopsystem.services.seed;

import java.io.IOException;

public interface SeedService {
    void seedAuthors() throws IOException;

    void seedBooks() throws IOException;

    void seedCategories() throws IOException;

    default void seedAllData() throws IOException {
        this.seedAuthors();
        this.seedCategories();
        this.seedBooks();
    }
}
