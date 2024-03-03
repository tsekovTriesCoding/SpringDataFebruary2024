package org.example.usersystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.example.usersystem.annotations.email.Email;
import org.example.usersystem.annotations.password.Password;
import org.example.usersystem.constants.Constants;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    @Length(min = 4, max = 30, message = Constants.USERNAME_INCORRECT_LENGTH)
    private String username;

    @Column(nullable = false)
    @Password(minLength = 6,
            maxLength = 50,
            containsDigit = true,
            containsLowerCase = true,
            containsUpperCase = true,
            containsSpecialSymbols = true)
    private String password;
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "registered_on")
    private LocalDateTime registeredOn;

    @Column(name = "last_time_logged_in")
    private LocalDateTime lastTimeLoggedIn;

    @Column
    @Min(value = 1, message = Constants.AGE_TOO_LOW)
    @Max(value = 120, message = Constants.AGE_TOO_HIGH)
    private int age;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    private Town bornInTown;

    @ManyToOne
    private Town currentTown;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany
    @JoinTable(name = "friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private Set<User> friends;

    @Lob
    @Size(max = 1024 * 1024)
    private byte[] profilePicture;

    public User() {
    }

    private User(builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.registeredOn = builder.registeredOn;
        this.lastTimeLoggedIn = builder.lastTimeLoggedIn;
        this.age = builder.age;
        this.isDeleted = builder.isDeleted;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Town getBornInTown() {
        return bornInTown;
    }

    public void setBornInTown(Town bornInTown) {
        this.bornInTown = bornInTown;
    }

    public Town getCurrentTown() {
        return currentTown;
    }

    public void setCurrentTown(Town currentTown) {
        this.currentTown = currentTown;
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

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public static class builder {
        private String username;
        private String password;
        private String email;
        private LocalDateTime registeredOn;
        private LocalDateTime lastTimeLoggedIn;
        private int age;
        private boolean isDeleted;
        private String firstName;
        private String lastName;

        private builder() {
        }

        public static builder newInstance() {
            return new builder();
        }

        public builder username(String username) {
            this.username = username;
            return this;
        }

        public builder password(String password) {
            this.password = password;
            return this;
        }

        public builder email(String email) {
            this.email = email;
            return this;
        }

        public builder registeredOn(LocalDateTime registeredOn) {
            this.registeredOn = registeredOn;
            return this;
        }

        public builder lastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
            this.lastTimeLoggedIn = lastTimeLoggedIn;
            return this;
        }

        public builder age(int age) {
            this.age = age;
            return this;
        }

        public builder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
