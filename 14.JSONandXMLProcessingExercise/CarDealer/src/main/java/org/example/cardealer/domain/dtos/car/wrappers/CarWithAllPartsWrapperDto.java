package org.example.cardealer.domain.dtos.car.wrappers;

import org.example.cardealer.domain.dtos.part.wrappers.AllPartsWrapperDto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarWithAllPartsWrapperDto {
    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private Double travelledDistance;
    @XmlElement(name = "parts")
    private AllPartsWrapperDto parts;

    public CarWithAllPartsWrapperDto() {
    }

    public CarWithAllPartsWrapperDto(String make, String model, Double travelledDistance, AllPartsWrapperDto parts) {
        this.make = make;
        this.model = model;
        this.travelledDistance = travelledDistance;
        this.parts = parts;
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

    public AllPartsWrapperDto getParts() {
        return parts;
    }

    public void setParts(AllPartsWrapperDto parts) {
        this.parts = parts;
    }
}
