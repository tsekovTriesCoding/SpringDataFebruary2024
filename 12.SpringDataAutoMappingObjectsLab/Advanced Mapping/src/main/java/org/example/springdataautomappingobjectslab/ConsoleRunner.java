package org.example.springdataautomappingobjectslab;

import org.example.springdataautomappingobjectslab.entities.Employee;
import org.example.springdataautomappingobjectslab.entities.dtos.ManagerDto;
import org.example.springdataautomappingobjectslab.services.EmployeeService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final EmployeeService employeeService;

    @Autowired
    public ConsoleRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Employee empManager = new Employee("Boss", "Boss", BigDecimal.TEN);
//
        Employee employee = new Employee("Anton", "Petrov", BigDecimal.TEN, empManager);
//        Employee employee1 = new Employee("Boqn", "Germanov", BigDecimal.TEN, empManager);
        this.employeeService.addEmployee(employee);

        List<Employee> all = this.employeeService.findAll();

        Employee manager = all.get(0);
        manager.addSubordinate(employee);

        ModelMapper mapper = new ModelMapper();
        for (Employee emp : all) {
            ManagerDto map = mapper.map(emp, ManagerDto.class);

            System.out.println(map);
        }

    }
}
