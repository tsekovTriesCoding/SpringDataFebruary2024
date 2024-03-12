package org.example.cardealer.domain.dtos.supplier;

import org.example.cardealer.domain.entities.Part;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalSupplierDto {
    @XmlAttribute
    private long id;
    @XmlAttribute
    private String name;
    @XmlAttribute(name = "parts-count")
    private Integer partsCount;
    private Set<Part> parts;

    public LocalSupplierDto() {
    }

    public LocalSupplierDto(long id, String name, Set<Part> parts) {
        this.id = id;
        this.name = name;
        this.partsCount = parts.size();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
    }

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

}
