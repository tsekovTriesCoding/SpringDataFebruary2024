package softuni.exam.instagraphlite.models.entity;

import softuni.exam.instagraphlite.models.dto.UserWithPostExportDto;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "profile_picture_id", nullable = false)
    private Picture picture;

    @OneToMany(targetEntity = Post.class, mappedBy = "user")
    private List<Post> posts;

    public User() {
    }

    public UserWithPostExportDto toUserExportDto(User user) {
        return new UserWithPostExportDto(user);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
