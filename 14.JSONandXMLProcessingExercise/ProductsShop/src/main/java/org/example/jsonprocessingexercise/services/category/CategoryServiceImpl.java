package org.example.jsonprocessingexercise.services.category;

import org.example.jsonprocessingexercise.domain.dtos.category.CategorySummaryDto;
import org.example.jsonprocessingexercise.domain.dtos.category.wrappers.CategoriesSummaryWrapperDto;
import org.example.jsonprocessingexercise.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.example.jsonprocessingexercise.constants.Paths.CATEGORIES_BY_PRODUCTS_COUNT_JSON_PATH;
import static org.example.jsonprocessingexercise.constants.Paths.CATEGORIES_BY_PRODUCTS_COUNT_XML_PATH;
import static org.example.jsonprocessingexercise.constants.Utils.writeJsonOnToFile;
import static org.example.jsonprocessingexercise.constants.Utils.writeXmlOnToFile;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategorySummaryDto> getCategorySummary() throws IOException, JAXBException {
        final List<CategorySummaryDto> categorySummaryDtos = this.categoryRepository
                .getCategorySummary().orElseThrow(NoSuchElementException::new);

        final JAXBContext context = JAXBContext.newInstance(CategoriesSummaryWrapperDto.class);
        final Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        final CategoriesSummaryWrapperDto categoriesSummaryWrapperDto = new CategoriesSummaryWrapperDto(categorySummaryDtos);

        writeJsonOnToFile(categorySummaryDtos, CATEGORIES_BY_PRODUCTS_COUNT_JSON_PATH);
        writeXmlOnToFile(categoriesSummaryWrapperDto, CATEGORIES_BY_PRODUCTS_COUNT_XML_PATH);

        return categorySummaryDtos;
    }
}
