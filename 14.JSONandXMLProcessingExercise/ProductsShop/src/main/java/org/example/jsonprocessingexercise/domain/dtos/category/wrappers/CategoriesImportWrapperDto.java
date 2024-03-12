package org.example.jsonprocessingexercise.domain.dtos.category.wrappers;

import org.example.jsonprocessingexercise.domain.dtos.category.CategoryImportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesImportWrapperDto {
    @XmlElement(name = "category")
    private List<CategoryImportDto> categories;

    public CategoriesImportWrapperDto() {
    }

    public CategoriesImportWrapperDto(List<CategoryImportDto> categories) {
        this.categories = categories;
    }

    public List<CategoryImportDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryImportDto> categories) {
        this.categories = categories;
    }
}
