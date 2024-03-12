package org.example.jsonprocessingexercise.domain.dtos.category;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryImportDto {
    @XmlElement
    private String name;

    public CategoryImportDto() {
    }

    public CategoryImportDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
