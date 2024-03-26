package softuni.exam.models.dto;

import softuni.exam.models.entity.Passenger;

public class PassengerExportDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Long countOfTickets;

    public PassengerExportDto(Passenger passenger) {
        this.firstName = passenger.getFirstName();
        this.lastName = passenger.getLastName();
        this.email = passenger.getEmail();
        this.phoneNumber = passenger.getPhoneNumber();
        this.countOfTickets = (long) passenger.getTickets().size();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCountOfTickets() {
        return countOfTickets;
    }

    public void setCountOfTickets(Long countOfTickets) {
        this.countOfTickets = countOfTickets;
    }

    @Override
    public String toString() {
        return String.format("Passenger %s  %s\n" +
                        "\tEmail - %s\n" +
                        "Phone - %s\n" +
                        "\tNumber of tickets - %d\n", this.firstName, this.lastName,
                this.email,
                this.phoneNumber,
                this.countOfTickets);
    }
}
