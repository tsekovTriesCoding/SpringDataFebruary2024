package org.example.cardealer.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    @Column(name = "discount_percentage")
    private Double discountPercentage;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Sale() {
    }

    public Sale(Double discountPercentage, Car car, Customer customer) {
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
