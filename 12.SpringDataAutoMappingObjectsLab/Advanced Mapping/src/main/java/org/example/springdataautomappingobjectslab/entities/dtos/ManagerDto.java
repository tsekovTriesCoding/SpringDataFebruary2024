package org.example.springdataautomappingobjectslab.entities.dtos;

import org.example.springdataautomappingobjectslab.entities.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerDto {
    private String firstName;
    private String lastName;
    private List<EmployeeDto> employees;

    public ManagerDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        String employees = this.employees
                .stream()
                .map(EmployeeDto::toString)
                .map(s -> "    - " + s)
                .collect(Collectors.joining(System.lineSeparator()));

        return this.firstName + " " + this.lastName + " | " + "Employees: " + this.employees.size() + System.lineSeparator()
                + employees;
    }
}
