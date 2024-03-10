package org.example.springdataadvancedqueryinglab.services.shampoo;

import org.example.springdataadvancedqueryinglab.enitites.Shampoo;
import org.example.springdataadvancedqueryinglab.enitites.Size;
import org.example.springdataadvancedqueryinglab.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findAllBySizeOrderById(String size) {
        Size parsed = Size.valueOf(size.toUpperCase());

        return this.shampooRepository.findAllBySizeOrderById(parsed);
    }

    @Override
    public List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(String size, Long labelId) {
        Size parsed = Size.valueOf(size.toUpperCase());

        return this.shampooRepository.findAllBySizeOrLabelIdOrderByPrice(parsed, labelId);
    }

    @Override
    public List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countAllByPriceLessThan(BigDecimal price) {
        return this.shampooRepository.countAllByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> findAllByIngredientsIn(List<String> names) {
        return this.shampooRepository.findAllByIngredientsIn(names);
    }

    @Override
    public List<Shampoo> findAllByIngredientsCountLessThan(int count) {
        return this.shampooRepository.findAllByIngredientsCountLessThan(count);
    }
}
