package org.example.cardealer.domain.dtos.car.wrappers;

import org.example.cardealer.domain.dtos.car.CarWithAllPartsDto;
import org.example.cardealer.domain.dtos.part.PartWithNameAndPriceDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsWithPartsWrapperDto {
    @XmlElement(name = "car")
    private List<CarWithAllPartsWrapperDto> cars;

    public CarsWithPartsWrapperDto() {
    }

    public CarsWithPartsWrapperDto(List<CarWithAllPartsDto> cars) {
        this.cars = CarWithAllPartsDto.toCarWithPartsWrapperDto(cars);
    }

    public List<CarWithAllPartsWrapperDto> getCars() {
        return cars;
    }

    public void setCars(List<CarWithAllPartsWrapperDto> cars) {
        this.cars = cars;
    }
}
