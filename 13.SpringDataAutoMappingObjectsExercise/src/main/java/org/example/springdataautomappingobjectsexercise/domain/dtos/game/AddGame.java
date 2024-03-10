package org.example.springdataautomappingobjectsexercise.domain.dtos.game;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.example.springdataautomappingobjectsexercise.constants.Exceptions.*;

public class AddGame {
    private String title;
    private BigDecimal price;
    private float size;
    private String trailer;
    private String thumbnailURL;
    private String description;
    private LocalDate releaseDate;

    public AddGame() {
    }

    public AddGame(String title,
                   BigDecimal price,
                   float size,
                   String trailer,
                   String thumbnailURL,
                   String description,
                   LocalDate releaseDate) {
        this.setTitle(title);
        this.setPrice(price);
        this.setSize(size);
        this.setTrailer(trailer);
        this.setThumbnailURL(thumbnailURL);
        this.setDescription(description);
        this.setReleaseDate(releaseDate);
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        if (!Character.isUpperCase(title.charAt(0)) || title.length() < 3 || title.length() > 100) {
            throw new IllegalArgumentException(INVALID_GAME_TITLE);
        }

        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IllegalArgumentException(INVALID_GAME_PRICE);
        }

        this.price = price;
    }

    public float getSize() {
        return size;
    }

    private void setSize(float size) {
        if (size < 0) {
            throw new IllegalArgumentException(INVALID_GAME_SIZE);
        }

        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    private void setTrailer(String trailer) {
        if (trailer.length() != 11) {
            throw new IllegalArgumentException(INVALID_TRAILER_ID_LENGTH);
        }

        this.trailer = trailer;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    private void setThumbnailURL(String thumbnailURL) {
        if (!thumbnailURL.startsWith("http://") && !thumbnailURL.startsWith("https://")) {
            throw new IllegalArgumentException(INVALID_THUMBNAIL_URL);
        }

        this.thumbnailURL = thumbnailURL;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        if (description.length() < 20) {
            throw new IllegalArgumentException(INVALID_DESCRIPTION);
        }

        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    private void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String addedGame() {
        return "Added " + this.title;
    }
}
