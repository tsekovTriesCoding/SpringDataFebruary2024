package softuni.exam.models.dto;

import java.time.LocalTime;

public class ForecastExportDto {
    private String cityName;
    private double minTemperature;
    private double maxTemperature;
    private LocalTime sunrise;
    private LocalTime sunset;

    public ForecastExportDto() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
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

    @Override
    public String toString() {
        return String.format("City: %s:%n-min temperature: %.2f%n--max temperature: %.2f%n---sunrise: %s%n----sunset: %s",
                this.cityName, this.minTemperature, this.maxTemperature, this.sunrise, this.sunset);
    }

}