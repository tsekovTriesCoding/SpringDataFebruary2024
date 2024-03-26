package softuni.exam.models.dto;

import softuni.exam.models.entity.Genre;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class BookImportDto {
    @NotNull
    @Size(min = 3, max = 40)
    private String author;

    @NotNull
    private boolean available;

    @NotNull
    @Size(min = 5)
    private String description;

    @NotNull
    private Genre genre;

    @NotNull
    @Size(min = 3, max = 40)
    private String title;

    @NotNull
    @Positive
    private double rating;

    public BookImportDto() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
