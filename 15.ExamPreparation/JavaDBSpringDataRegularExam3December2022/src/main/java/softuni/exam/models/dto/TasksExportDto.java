package softuni.exam.models.dto;

import java.text.DecimalFormat;
import java.util.Locale;

public class TasksExportDto {
    private String carMake;
    private String carModel;
    private int kilometers;
    private String firstName;
    private String lastName;
    private long id;
    private double engine;
    private double price;

    public TasksExportDto() {
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getEngine() {
        return engine;
    }

    public void setEngine(double engine) {
        this.engine = engine;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        Locale.setDefault(Locale.US);
        return String.format("Car %s %s with %dkm%n" +
                        "-Mechanic: %s %s - task №%d:%n" +
                        " --Engine: %s%n" +
                        "---Price: %.2f$", this.carMake, this.carModel, this.kilometers,
                this.firstName, this.lastName, this.id,
                this.engine,
                this.price);
    }
}
