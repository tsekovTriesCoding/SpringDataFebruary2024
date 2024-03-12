package org.example.jsonprocessingexercise.domain.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column
    private String name;

    @Column
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name ="buyer_id")
    @Fetch(FetchMode.JOIN)
    private User buyerID;

    @ManyToOne
    @JoinColumn(name ="seller_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    private User sellerID;

    @ManyToMany
    @JoinTable(
            name = "products_categories",
            joinColumns = {
                    @JoinColumn(name = "product_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id"),
            }
    )
    @Fetch(FetchMode.JOIN)
    private Set<Category> categories;

    public Product() {
    }

    public Product(String name, BigDecimal price, User buyerID, User sellerID, Set<Category> categories) {
        this.name = name;
        this.price = price;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.categories = categories;
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

    public User getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(User buyerID) {
        this.buyerID = buyerID;
    }

    public User getSellerID() {
        return sellerID;
    }

    public void setSellerID(User sellerID) {
        this.sellerID = sellerID;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
