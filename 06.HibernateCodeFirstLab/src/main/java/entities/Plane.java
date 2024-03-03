package entities;

import jakarta.persistence.*;
import relation.entities.Company;

@Entity(name = "planes")
public class Plane extends Vehicle {
    private static final String TYPE = "PLANE";
    @Column(name = "passenger_capacity")
    private Integer passengerCapacity;
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public Plane() {
        super(TYPE);
    }

    public Plane(String model, Integer passengerCapacity) {
        this();
        this.model = model;
        this.passengerCapacity = passengerCapacity;
    }

    public Integer getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(Integer passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
}
