package org.example.jsonprocessingexercise.services.product;

import org.example.jsonprocessingexercise.domain.dtos.product.ProductDto;
import org.example.jsonprocessingexercise.domain.dtos.product.ProductInPriceRangeWithNoBuyerDto;
import org.example.jsonprocessingexercise.domain.dtos.product.wrappers.ProductsInPriceRangeWithNoBuyerWrapperDto;
import org.example.jsonprocessingexercise.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.example.jsonprocessingexercise.constants.Paths.PRODUCTS_IN_RANGE_WITH_NO_BUYERS_JSON_PATH;
import static org.example.jsonprocessingexercise.constants.Paths.PRODUCTS_IN_RANGE_WITH_NO_BUYERS_XML_PATH;
import static org.example.jsonprocessingexercise.constants.Utils.MODEL_MAPPER;
import static org.example.jsonprocessingexercise.constants.Utils.writeJsonOnToFile;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductInPriceRangeWithNoBuyerDto> findAllByPriceBetweenAndBuyerIDIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException, JAXBException {
        List<ProductInPriceRangeWithNoBuyerDto> products = this.productRepository.findAllByPriceBetweenAndBuyerIDIsNullOrderByPrice(low, high)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(product -> MODEL_MAPPER.map(product, ProductDto.class))
                .map(ProductDto::ProductInPriceRangeWithNoBuyerDto)
                .toList();

        final ProductsInPriceRangeWithNoBuyerWrapperDto productsInPriceRangeWithNoBuyerWrapperDto =
                new ProductsInPriceRangeWithNoBuyerWrapperDto(products);

        final JAXBContext context = JAXBContext.newInstance(ProductsInPriceRangeWithNoBuyerWrapperDto.class);
        final Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        final File file = PRODUCTS_IN_RANGE_WITH_NO_BUYERS_XML_PATH.toFile();

        marshaller.marshal(productsInPriceRangeWithNoBuyerWrapperDto, file);
        writeJsonOnToFile(products, PRODUCTS_IN_RANGE_WITH_NO_BUYERS_JSON_PATH);

        return products;
    }
}
