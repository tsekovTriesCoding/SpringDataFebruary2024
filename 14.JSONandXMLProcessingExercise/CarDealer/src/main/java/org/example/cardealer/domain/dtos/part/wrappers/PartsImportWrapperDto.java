package org.example.cardealer.domain.dtos.part.wrappers;

import org.example.cardealer.domain.dtos.part.PartImportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartsImportWrapperDto {
    @XmlElement(name = "part")
    private List<PartImportDto> parts;

    public PartsImportWrapperDto() {
    }

    public List<PartImportDto> getParts() {
        return parts;
    }

    public void setParts(List<PartImportDto> parts) {
        this.parts = parts;
    }
}
