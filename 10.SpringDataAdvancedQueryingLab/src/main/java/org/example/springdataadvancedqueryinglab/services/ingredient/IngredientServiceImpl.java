package org.example.springdataadvancedqueryinglab.services.ingredient;

import jakarta.transaction.Transactional;
import org.example.springdataadvancedqueryinglab.enitites.Ingredient;
import org.example.springdataadvancedqueryinglab.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findAllByNameStartingWith(String startLetters) {
        return this.ingredientRepository.findAllByNameStartingWith(startLetters);
    }

    @Override
    public List<Ingredient> findAllByNameIn(List<String> names) {
        return this.ingredientRepository.findAllByNameInOrderByPrice(names);
    }

    @Override
    @Transactional
    public void deleteAllByName(String name) {
        this.ingredientRepository.deleteAllByName(name);
    }

    @Override
    @Transactional
    public void updateAllIngredientsPrices() {
        this.ingredientRepository.updateAllIngredientsPrices();
    }

    @Override
    @Transactional
    public void updateAllIngredientsPricesByNameIn(List<String> names) {
        this.ingredientRepository.updateAllIngredientsPricesByNameIn(names);
    }
}
