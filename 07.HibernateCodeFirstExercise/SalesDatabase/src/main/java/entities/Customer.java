package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column
    private String email;
    @Column(name = "credit_card_number", nullable = false)
    private String creditCardNumber;
    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales;

}
