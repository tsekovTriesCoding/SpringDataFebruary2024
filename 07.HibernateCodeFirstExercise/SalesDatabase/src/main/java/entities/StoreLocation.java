package entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "store_locations")
public class StoreLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "location_name")
    private String locationName;
    @OneToMany(mappedBy = "storeLocation")
    private Set<Sale> sales;
}
