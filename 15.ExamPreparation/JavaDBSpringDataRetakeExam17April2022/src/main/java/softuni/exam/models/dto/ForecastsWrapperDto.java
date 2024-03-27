package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastsWrapperDto {

    @XmlElement(name = "forecast")
    private List<ForecastImportDto> forecasts;

    public ForecastsWrapperDto() {
    }

    public List<ForecastImportDto> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastImportDto> forecasts) {
        this.forecasts = forecasts;
    }
}
