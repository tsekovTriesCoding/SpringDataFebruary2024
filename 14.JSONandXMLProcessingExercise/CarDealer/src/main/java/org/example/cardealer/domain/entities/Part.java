package org.example.cardealer.domain.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity {
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @Fetch(FetchMode.JOIN)
    private PartSupplier supplier;

    public Part() {
    }

    public Part(String name, BigDecimal price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public PartSupplier getSupplier() {
        return supplier;
    }

    public void setSupplier(PartSupplier supplier) {
        this.supplier = supplier;
    }
}
