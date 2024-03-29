package softuni.exam.models.dto;

import softuni.exam.util.XmlParser;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "astronomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerImportDto {
    @NotNull
    @DecimalMin(value = "500.0")
    @XmlElement(name = "average_observation_hours")
    private double averageObservationHours;

    @XmlElement
    @XmlJavaTypeAdapter(XmlParser.LocalDateAdapter.class)
    private LocalDate birthday;

    @NotNull
    @Size(min = 2, max = 30)
    @XmlElement(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    @XmlElement(name = "last_name")
    private String lastName;

    @NotNull
    @DecimalMin(value = "15000.0")
    @XmlElement
    private double salary;

    @NotNull
    @XmlElement(name = "observing_star_id")
    private long observingStarId;

    public AstronomerImportDto() {
    }

    public double getAverageObservationHours() {
        return averageObservationHours;
    }

    public void setAverageObservationHours(double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getObservingStarId() {
        return observingStarId;
    }

    public void setObservingStarId(long observingStarId) {
        this.observingStarId = observingStarId;
    }
}
