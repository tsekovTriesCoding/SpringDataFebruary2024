package softuni.exam.models.dto;

public class VolcanoExportDto {
    private String name;
    private String countryName;
    private int elevation;
    private String lastEruption;

    public VolcanoExportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(String lastEruption) {
        this.lastEruption = lastEruption;
    }

    @Override
    public String toString() {
        return String.format("Volcano: %s\n" +
                "   *Located in: %s\n" +
                "   **Elevation: %d\n" +
                "   ***Last eruption on: %s", this.name, this.countryName, this.elevation, this.lastEruption);
    }
}
