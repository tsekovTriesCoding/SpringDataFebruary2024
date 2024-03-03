package relation.entities;

import entities.Car;
import jakarta.persistence.*;

@Entity(name = "plate_numbers")
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String number;
    @OneToOne(targetEntity = Car.class , mappedBy = "plateNumber")
    private Car bus;

    public PlateNumber() {
    }

    public PlateNumber(String number) {
        this.number = number;
    }
}
