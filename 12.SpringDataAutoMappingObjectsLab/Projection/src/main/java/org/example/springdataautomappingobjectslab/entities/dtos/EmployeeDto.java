package org.example.springdataautomappingobjectslab.entities.dtos;

import java.math.BigDecimal;

public class EmployeeDto {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String managerLastName;

    public EmployeeDto() {
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }

    @Override
    public String toString() {
        String managerLastName = this.managerLastName == null ? "[no manager]" : this.managerLastName;
        return this.firstName + " " + this.lastName + " " + this.salary + " - Manager: " + managerLastName;
    }
}
