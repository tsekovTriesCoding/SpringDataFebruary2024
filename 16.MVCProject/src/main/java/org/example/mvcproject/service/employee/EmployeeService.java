package org.example.mvcproject.service.employee;

import javax.xml.bind.JAXBException;

public interface EmployeeService {
    void importEmployees() throws JAXBException;

    boolean areImported();

    String exportEmployeesAbove();
}
