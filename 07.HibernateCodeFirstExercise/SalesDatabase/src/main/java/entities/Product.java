package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column
    private Double quantity;
    @Column(nullable = false)
    private BigDecimal price;
    @OneToMany(mappedBy = "product")
    private Set<Sale> sales;

}
