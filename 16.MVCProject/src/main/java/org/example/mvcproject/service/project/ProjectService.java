package org.example.mvcproject.service.project;

import javax.xml.bind.JAXBException;

public interface ProjectService {
    void importProjects() throws JAXBException;

    boolean areImported();

    String exportFinishedProjects();
}
