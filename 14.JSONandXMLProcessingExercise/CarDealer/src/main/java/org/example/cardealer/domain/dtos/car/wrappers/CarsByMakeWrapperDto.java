package org.example.cardealer.domain.dtos.car.wrappers;

import org.example.cardealer.domain.dtos.car.CarBasicInfoDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsByMakeWrapperDto {
    @XmlElement(name = "car")
    private List<CarBasicInfoDto> cars;

    public CarsByMakeWrapperDto() {
    }

    public CarsByMakeWrapperDto(List<CarBasicInfoDto> cars) {
        this.cars = cars;
    }

    public List<CarBasicInfoDto> getCars() {
        return cars;
    }

    public void setCars(List<CarBasicInfoDto> cars) {
        this.cars = cars;
    }
}
