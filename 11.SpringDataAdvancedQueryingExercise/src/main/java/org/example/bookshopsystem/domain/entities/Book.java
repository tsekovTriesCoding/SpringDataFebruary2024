package org.example.bookshopsystem.domain.entities;

import jakarta.persistence.*;
import org.example.bookshopsystem.domain.enums.AgeRestriction;
import org.example.bookshopsystem.domain.enums.EditionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private EditionType editionType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int copies;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @ManyToOne
    private Author author;

    @ManyToMany
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories;

    public Book() {
    }

    public Book(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.editionType = builder.editionType;
        this.price = builder.price;
        this.copies = builder.copies;
        this.releaseDate = builder.releaseDate;
        this.ageRestriction = builder.ageRestriction;
        this.author = builder.author;
        this.categories = builder.categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getBookTitleReleaseDateCopiesFormat() {
        return this.title + " " + this.releaseDate + " " + this.copies;
    }

    public String getBookTitleAndPriceFormat() {
        return this.title + " - $" + this.price;
    }

    public String getBookTitleEditionTypeAndPriceFormat() {
        return this.title + " " + this.editionType + " " + this.price;
    }

    public String getBookTitleAndAuthorFullNameFormat() {
        return this.title + " (" + this.author.getAuthorFullName() + ")";
    }

    public static class Builder {
        private String title;

        private String description;

        private EditionType editionType;

        private BigDecimal price;

        private int copies;

        private LocalDate releaseDate;

        private AgeRestriction ageRestriction;

        private Author author;
        private Set<Category> categories;

        private Builder() {
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setEditionType(EditionType editionType) {
            this.editionType = editionType;
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder setCopies(int copies) {
            this.copies = copies;
            return this;
        }

        public Builder setReleaseDate(LocalDate date) {
            this.releaseDate = date;
            return this;
        }

        public Builder setAgeRestriction(AgeRestriction ageRestriction) {
            this.ageRestriction = ageRestriction;
            return this;
        }

        public Builder setAuthor(Author author) {
            this.author = author;
            return this;
        }

        public Builder setCategories(Set<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Book build() {
            return new Book(this);
        }

    }
}
