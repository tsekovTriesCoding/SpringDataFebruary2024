package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name", length = 50, unique = true, nullable = false)
    private String lastName;
    @Column
    private String address;
    @Column
    private String email;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column
    private String picture;
    @Column(name = "has_medical_insurance", nullable = false)
    private Boolean hasMedicalInsurance;

    public Patient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getHasMedicalInsurance() {
        return hasMedicalInsurance;
    }

    public void setHasMedicalInsurance(Boolean hasMedicalInsurance) {
        this.hasMedicalInsurance = hasMedicalInsurance;
    }

    @Override
    public String toString() {
        String hasInsurance = "No";
        String firstName = "-";
        String address = "-";
        String email = "-";
        String picture = "-";
        if (hasMedicalInsurance) {
            hasInsurance = "Yes";
        }

        if (this.firstName != null) {
            firstName = this.firstName;
        }

        if (this.address != null) {
            address = this.address;
        }

        if (this.email != null) {
            email = this.email;
        }

        if (this.picture != null) {
            picture = this.picture;
        }

        return String.format("First name : %s, Last name: %s, Address: %s, Email: %s,  Picture: %s, Valid insurance: %s",
                firstName, this.lastName, address, email, picture, hasInsurance);
    }
}
