package org.example.cardealer.domain.dtos.customer;

import org.example.cardealer.domain.entities.Sale;

import java.time.LocalDateTime;
import java.util.Set;

public class CustomerDto {
    private String name;
    private LocalDateTime birthdate;
    private Boolean isYoungDriver;
    private Set<Sale> sales;

    public CustomerDto() {
    }

    public CustomerDto(String name, LocalDateTime birthdate, Boolean isYoungDriver, Set<Sale> sales) {
        this.name = name;
        this.birthdate = birthdate;
        this.isYoungDriver = isYoungDriver;
        this.sales = sales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
