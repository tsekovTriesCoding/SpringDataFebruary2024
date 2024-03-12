package org.example.cardealer.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Column
    private String name;
    @Column
    private LocalDateTime birthdate;
    @Column
    private Boolean isYoungDriver;
    @OneToMany(targetEntity = Sale.class, mappedBy = "customer")
    private Set<Sale> sales;

    public Customer() {
    }

    public Customer(String name, LocalDateTime birthdate, Boolean isYoungDriver, Set<Sale> sales) {
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
}

