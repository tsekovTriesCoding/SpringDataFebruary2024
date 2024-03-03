package relation.entities;

import entities.Truck;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name")
    private String fullName;
    @ManyToMany
    @JoinTable(name = "drivers_trucks",
            joinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "truck_id", referencedColumnName = "id"))
    private List<Truck> trucks;

    public Driver(String fullName) {
        this.fullName = fullName;
        this.trucks = new ArrayList<>();
    }
}
