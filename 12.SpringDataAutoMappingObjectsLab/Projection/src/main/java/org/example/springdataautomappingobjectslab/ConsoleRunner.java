package org.example.springdataautomappingobjectslab;

import org.example.springdataautomappingobjectslab.entities.Employee;
import org.example.springdataautomappingobjectslab.entities.dtos.EmployeeDto;
import org.example.springdataautomappingobjectslab.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final EmployeeService employeeService;

    @Autowired
    public ConsoleRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Employee empManager = new Employee("Boss", "BigBoss", BigDecimal.valueOf(2000),
                null, LocalDate.of(1980, 3, 21));
        Employee employee = new Employee("Anton", "Petrov", BigDecimal.TEN,
                empManager, LocalDate.of(1987, 1, 1));

        this.employeeService.addEmployee(employee);


        List<Employee> allByBirthdayBefore = this.employeeService.findAllByBirthdayBeforeOrderBySalaryDesc(1990);

        ModelMapper mapper = new ModelMapper();
        for (Employee emp : allByBirthdayBefore) {
            EmployeeDto map = mapper.map(emp, EmployeeDto.class);
            System.out.println(map);
        }


    }
}
