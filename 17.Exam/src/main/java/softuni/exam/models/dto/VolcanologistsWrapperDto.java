package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistsWrapperDto {
    @XmlElement(name = "volcanologist")
    private List<VolcanologistsImportDto> volcanologists;

    public VolcanologistsWrapperDto() {
    }

    public List<VolcanologistsImportDto> getVolcanologists() {
        return volcanologists;
    }

    public void setVolcanologists(List<VolcanologistsImportDto> volcanologists) {
        this.volcanologists = volcanologists;
    }
}
