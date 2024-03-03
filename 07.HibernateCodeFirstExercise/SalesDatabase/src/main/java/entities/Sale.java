package entities;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private StoreLocation storeLocation;
    @Column
    private Date date;

}
