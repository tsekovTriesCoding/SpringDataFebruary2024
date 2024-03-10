package org.example.springdataautomappingobjectsexercise.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column
    private String trailer;
    @Column(name = "thumbnail_url")
    private String thumbnailURL;
    @Column(nullable = false)
    private float size;
    @Column(nullable = false)
    private BigDecimal price;
    @Column
    private String description;
    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Game() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String gameTitleAndPriceFormat() {
        return this.title + " " + this.price;
    }

    public String gameDetailsFormat() {
        return String.format("Title: %s%nPrice: %s%nDescription: %s%nRelease date: %s",
                this.title,
                this.price,
                this.description,
                this.releaseDate);
    }
}
