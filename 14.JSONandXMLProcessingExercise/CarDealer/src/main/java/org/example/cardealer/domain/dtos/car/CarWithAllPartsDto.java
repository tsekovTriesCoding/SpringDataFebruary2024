package org.example.cardealer.domain.dtos.car;

import org.example.cardealer.domain.dtos.car.wrappers.CarWithAllPartsWrapperDto;
import org.example.cardealer.domain.dtos.car.wrappers.CarsWithPartsWrapperDto;
import org.example.cardealer.domain.dtos.part.PartWithNameAndPriceDto;
import org.example.cardealer.domain.dtos.part.wrappers.AllPartsWrapperDto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CarWithAllPartsDto {
    private String make;
    private String model;
    private Double travelledDistance;
    private List<PartWithNameAndPriceDto> parts;

    public CarWithAllPartsDto() {
    }

    public static List<CarWithAllPartsWrapperDto> toCarWithPartsWrapperDto(List<CarWithAllPartsDto> cars) {
        return cars.stream()
                .map(carWithAllPartsDto -> new CarWithAllPartsWrapperDto
                        (carWithAllPartsDto.getMake(), carWithAllPartsDto.getModel(),
                                carWithAllPartsDto.getTravelledDistance(),
                                new AllPartsWrapperDto(carWithAllPartsDto.getParts()))
                ).collect(Collectors.toList());
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

    public Double getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Double travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<PartWithNameAndPriceDto> getParts() {
        return parts;
    }

    public void setParts(List<PartWithNameAndPriceDto> parts) {
        this.parts = parts;
    }
}
