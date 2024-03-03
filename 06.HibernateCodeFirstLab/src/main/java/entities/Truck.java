package entities;

import jakarta.persistence.*;
import relation.entities.Driver;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "trucks")
public class Truck extends Vehicle {
    private static final String TYPE = "TRUCK";
    @Column(name = "load_capacity")
    private Double loadCapacity;
    @ManyToMany
    private List<Driver> drivers;

    public Truck(Double loadCapacity, Driver driver) {
        super(TYPE);
        this.loadCapacity = loadCapacity;
        this.drivers = new ArrayList<>();
    }

    public Double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(Double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
