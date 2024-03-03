package org.example.usersystem.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Town extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false)
    private Country country;

    @OneToMany(mappedBy = "bornInTown")
    private Set<User> bornInCitizens;

    @OneToMany(mappedBy = "currentTown")
    private Set<User> currentCitizens;
}
