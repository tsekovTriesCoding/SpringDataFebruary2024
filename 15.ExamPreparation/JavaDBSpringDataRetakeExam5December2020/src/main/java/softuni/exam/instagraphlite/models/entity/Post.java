package softuni.exam.instagraphlite.models.entity;

import softuni.exam.instagraphlite.models.dto.PostDetailsDto;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Column(nullable = false)
    private String caption;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Picture picture;

    public Post() {
    }

    public PostDetailsDto toPostDetailsDto (Post post) {
        return new PostDetailsDto(post);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
