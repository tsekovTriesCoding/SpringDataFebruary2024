package org.example.jsonprocessingexercise.domain.dtos.category.wrappers;

import org.example.jsonprocessingexercise.domain.dtos.category.CategorySummaryDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesSummaryWrapperDto {
    @XmlElement(name = "category")
    private List<CategorySummaryDto> categories;

    public CategoriesSummaryWrapperDto() {
    }

    public CategoriesSummaryWrapperDto(List<CategorySummaryDto> categories) {
        this.categories = categories;
    }

    public List<CategorySummaryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorySummaryDto> categories) {
        this.categories = categories;
    }
}
