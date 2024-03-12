package org.example.jsonprocessingexercise.services.seed;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {
    void seedUser() throws IOException, JAXBException;

    void seedCategories() throws IOException, JAXBException;

    void seedProducts() throws IOException, JAXBException;

    default void seedAll() throws IOException, JAXBException {
        this.seedUser();
        this.seedCategories();
        this.seedProducts();
    }

}
