package softuni.exam.models.dto;

import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.util.XmlParser;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;

@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDto {
    @NotNull
    @XmlElement(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @NotNull
    @DecimalMin(value = "-20.0")
    @DecimalMax(value = "60.0")
    @XmlElement(name = "max_temperature")
    private double maxTemperature;

    @NotNull
    @DecimalMin(value = "-50.0")
    @DecimalMax(value = "40.0")
    @XmlElement(name = "min_temperature")
    private double minTemperature;

    @NotNull
    @XmlElement
    @XmlJavaTypeAdapter(XmlParser.LocalTimeAdapter.class)
    private LocalTime sunrise;

    @NotNull
    @XmlElement
    @XmlJavaTypeAdapter(XmlParser.LocalTimeAdapter.class)
    private LocalTime sunset;

    @XmlElement
    private Long city;

    public ForecastImportDto() {
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalTime sunset) {
        this.sunset = sunset;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }
}
