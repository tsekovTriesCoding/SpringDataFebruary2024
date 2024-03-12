package org.example.cardealer.domain.dtos.car;

import org.example.cardealer.domain.entities.Part;
import org.example.cardealer.domain.entities.Sale;

import java.util.List;
import java.util.Set;

public class CarDto {
    private String make;
    private String model;
    private Double travelledDistance;
    private Set<Sale> sales;
    private List<Part> parts;

    public CarDto() {
    }

    public CarDto(String make, String model, Double travelledDistance, Set<Sale> sales, List<Part> parts) {
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
