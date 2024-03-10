package org.example.bookshopsystem.domain.dto;

public class AuthorWithTotalCopiesCount {
    private final String firstNameName;
    private final String lastName;

    private final long count;

    public AuthorWithTotalCopiesCount(String firstNameName, String lastName, long count) {
        this.firstNameName = firstNameName;
        this.lastName = lastName;
        this.count = count;
    }

    public String getFirstNameName() {
        return firstNameName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return this.firstNameName + " " + this.lastName + " " + this.count;
    }
}
