package org.example.cardealer.domain.entities;

import jakarta.persistence.*;
import org.example.cardealer.domain.dtos.car.CarWithDiscountDto;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    @Column
    private String make;
    @Column
    private String model;
    @Column(name = "travelled_distance")
    private Double travelledDistance;
    @OneToMany(targetEntity = Sale.class, mappedBy = "car")
    @Fetch(FetchMode.JOIN)
    private Set<Sale> sales;
    @ManyToMany
    @JoinTable(
            name = "parts_cars",
            joinColumns = {
                    @JoinColumn(name = "part_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "car_id"),
            }
    )
    @Fetch(FetchMode.JOIN)
    private List<Part> parts;

    public Car() {
    }

    public Car(String make, String model, Double travelledDistance, Set<Sale> sales, List<Part> parts) {
        this.make = make;
        this.model = model;
        this.travelledDistance = travelledDistance;
        this.sales = sales;
        this.parts = parts;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Double travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
