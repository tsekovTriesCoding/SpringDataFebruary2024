package relation.entities;

import entities.Plane;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(targetEntity = Plane.class, mappedBy = "company")
    private List<Plane> planes;

    public Company(String name) {
        this.name = name;
        this.planes = new ArrayList<>();
    }
}
