package org.example.springdataautomappingobjectslab.services;

import org.example.springdataautomappingobjectslab.entities.Employee;
import org.example.springdataautomappingobjectslab.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addEmployee(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override
    public Employee findById(long id) {
        return this.employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Employee> findAllByManagerId(long id) {
        return this.employeeRepository.findAllByManagerId(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }


}
