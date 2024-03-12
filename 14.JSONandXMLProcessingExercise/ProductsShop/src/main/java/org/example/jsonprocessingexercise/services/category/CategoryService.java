package org.example.jsonprocessingexercise.services.category;

import org.example.jsonprocessingexercise.domain.dtos.category.CategorySummaryDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<CategorySummaryDto> getCategorySummary() throws IOException, JAXBException;
}
