package org.example.jsonprocessingexercise.services.product;

import org.example.jsonprocessingexercise.domain.dtos.product.ProductInPriceRangeWithNoBuyerDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductInPriceRangeWithNoBuyerDto> findAllByPriceBetweenAndBuyerIDIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException, JAXBException;
}
