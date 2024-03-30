package org.example.mvcproject.service.company;

import javax.xml.bind.JAXBException;

public interface CompanyService {
    void importCompanies() throws JAXBException;

    boolean areImported();
}
