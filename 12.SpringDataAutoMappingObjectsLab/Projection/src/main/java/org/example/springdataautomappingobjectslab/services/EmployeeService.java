package org.example.springdataautomappingobjectslab.services;

import org.example.springdataautomappingobjectslab.entities.Employee;

import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee employee);
    Employee findById(long id);
    List<Employee> findAllByManagerId(long id);
    List<Employee> findAll();
    List<Employee> findAllByBirthdayBeforeOrderBySalaryDesc(int year);
}
