package org.example.cardealer.domain.dtos.sale;

import org.example.cardealer.domain.entities.Car;
import org.example.cardealer.domain.entities.Customer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class SaleImportDto {
    private Double discountPercentage;
    private Car car;
    private Customer customer;

    public SaleImportDto() {
    }

    public SaleImportDto(Double discountPercentage, Car car, Customer customer) {
        this.discountPercentage = discountPercentage;
        this.car = car;
        this.customer = customer;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
