package entities;

import jakarta.persistence.Entity;

@Entity(name = "bikes")
public class Bike extends Vehicle {
    private static final String TYPE = "BIKE";

    public Bike() {
        super(TYPE);
    }
}
