package org.example.usersystem.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "albums")
public class Album extends BaseEntity {
    @Column
    private String name;

    @Column
    private String backgroundColor;

    @Column
    private boolean isPublic;

    @ManyToMany(mappedBy = "albums")
    private Set<Picture> pictures;

    @ManyToOne(optional = false)
    private User owner;
}
