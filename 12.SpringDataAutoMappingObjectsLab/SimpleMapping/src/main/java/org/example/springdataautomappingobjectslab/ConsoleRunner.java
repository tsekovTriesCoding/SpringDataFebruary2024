package org.example.springdataautomappingobjectslab;

import org.example.springdataautomappingobjectslab.entities.Employee;
import org.example.springdataautomappingobjectslab.entities.dtos.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ModelMapper mapper = new ModelMapper();

        Employee employee = new Employee("Anton", "Petrov", BigDecimal.TEN);

        EmployeeDTO employeeInfo = mapper.map(employee, EmployeeDTO.class);

        System.out.println(employeeInfo);
    }
}
