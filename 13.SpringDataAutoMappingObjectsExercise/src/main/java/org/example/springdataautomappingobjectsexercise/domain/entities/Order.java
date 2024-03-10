package org.example.springdataautomappingobjectsexercise.domain.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne
    private User user;
    @ManyToMany(targetEntity = Game.class, fetch = FetchType.EAGER)
    private Set<Game> products;

    public Order() {
        this.products = new HashSet<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Game> getProducts() {
        return products;
    }

    public void setProducts(Set<Game> products) {
        this.products = products;
    }
}
