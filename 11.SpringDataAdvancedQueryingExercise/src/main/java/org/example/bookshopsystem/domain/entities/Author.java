package org.example.bookshopsystem.domain.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @OneToMany(targetEntity = Book.class, mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Book> books;

    public Author() {
    }

    public Author(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public String getAuthorFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getAuthorFullNameAndCountOfBooks() {
        return this.firstName + " " + this.lastName + " " + this.books.size();
    }

    public static class Builder {
        private String firstName;
        private String lastName;

        private Builder() {
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Author build() {
            return new Author(this);
        }

    }

}
