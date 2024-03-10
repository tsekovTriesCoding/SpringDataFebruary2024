package org.example.springdataadvancedqueryinglab.services.shampoo;

import org.example.springdataadvancedqueryinglab.enitites.Shampoo;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<Shampoo> findAllBySizeOrderById(String size);
    List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(String size, Long labelId);
    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);
    int countAllByPriceLessThan(BigDecimal price);
    List<Shampoo> findAllByIngredientsIn(List<String> names);
    List<Shampoo> findAllByIngredientsCountLessThan(int count);
}
