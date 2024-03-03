package org.example.usersystem.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Country extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany
    @JoinTable(name = "countries_towns",
            joinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "town_id", referencedColumnName = "id"))
    private Set<Town> towns;

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Town> getTowns() {
        return towns;
    }

    public void setTowns(Set<Town> towns) {
        this.towns = towns;
    }
}
