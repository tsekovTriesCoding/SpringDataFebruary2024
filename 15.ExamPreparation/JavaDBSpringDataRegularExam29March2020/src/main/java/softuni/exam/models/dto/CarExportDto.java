package softuni.exam.models.dto;

import softuni.exam.models.entity.Car;

import java.time.LocalDate;

public class CarExportDto {
    private String make;
    private String model;
    private int kilometers;
    private LocalDate registeredOn;
    private int numberOfPictures;

    public CarExportDto(Car car) {
        this.make = car.getMake();
        this.model = car.getModel();
        this.kilometers = car.getKilometers();
        this.registeredOn = car.getRegisteredOn();
        this.numberOfPictures = car.getPictures().size();
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    public int getNumberOfPictures() {
        return numberOfPictures;
    }

    public void setNumberOfPictures(int numberOfPictures) {
        this.numberOfPictures = numberOfPictures;
    }

    @Override
    public String toString() {
        return String.format("Car make - %s, model - %s\n" +
                        "\tKilometers - %d\n" +
                        "\tRegistered on - %s\n" +
                        "\tNumber of pictures - %d", this.make, this.model,
                this.kilometers,
                this.registeredOn,
                this.numberOfPictures);
    }
}
