package org.example.springdataadvancedqueryinglab.services.ingredient;

import org.example.springdataadvancedqueryinglab.enitites.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAllByNameStartingWith(String startLetters);
    List<Ingredient> findAllByNameIn(List<String> names);
    void deleteAllByName(String name);
    void updateAllIngredientsPrices();
    void updateAllIngredientsPricesByNameIn(List<String> names);
}
