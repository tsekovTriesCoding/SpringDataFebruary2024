package entities;

import jakarta.persistence.*;
import relation.entities.PlateNumber;

@Entity(name = "cars")
public class Car extends Vehicle {
    private static final String TYPE = "CAR";
    @Basic
    private Integer seats;
    @OneToOne
    @JoinColumn(name = "plate_number_id", referencedColumnName = "id")
    private PlateNumber plateNumber;

    public Car() {
        super(TYPE);
    }

    public Car(String model, String fuelType, Integer seats,PlateNumber plateNumber) {
        this();
        this.model = model;
        this.fuelType = fuelType;
        this.seats = seats;
        this.plateNumber = plateNumber;
    }
    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}
