package org.example.cardealer.domain.dtos.part.wrappers;

import org.example.cardealer.domain.dtos.part.PartWithNameAndPriceDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class AllPartsWrapperDto {
    @XmlElement(name = "part")
    private List<PartWithNameAndPriceDto> parts;

    public AllPartsWrapperDto() {
    }

    public AllPartsWrapperDto(List<PartWithNameAndPriceDto> parts) {
        this.parts = parts;
    }

    public List<PartWithNameAndPriceDto> getParts() {
        return parts;
    }

    public void setParts(List<PartWithNameAndPriceDto> parts) {
        this.parts = parts;
    }
}
