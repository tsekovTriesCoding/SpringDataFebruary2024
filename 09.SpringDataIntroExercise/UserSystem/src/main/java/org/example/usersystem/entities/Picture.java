package org.example.usersystem.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Picture extends BaseEntity {
    @Column
    private String title;

    @Column
    private String caption;

    @Column(name = "path_on_file_system")
    private String pathOnFileSystem;

    @ManyToMany
    @JoinTable(name = "pictures_albums",
            joinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"))
    private Set<Album> albums;

}
