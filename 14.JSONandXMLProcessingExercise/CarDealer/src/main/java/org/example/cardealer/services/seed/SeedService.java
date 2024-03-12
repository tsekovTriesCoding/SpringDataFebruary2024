package org.example.cardealer.services.seed;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {
    void seedSuppliers() throws IOException, JAXBException;

    void seedParts() throws IOException, JAXBException;

    void seedCars() throws IOException, JAXBException;

    void seedCustomers() throws IOException, JAXBException;

    void seedSales();

    default void seedAll() throws IOException, JAXBException {
        this.seedSuppliers();
        this.seedParts();
        this.seedCars();
        this.seedCustomers();
        this.seedSales();
    }
}
