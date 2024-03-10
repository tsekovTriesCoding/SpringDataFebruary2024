package org.example.springdataadvancedqueryinglab.repositories;

import org.example.springdataadvancedqueryinglab.enitites.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByNameStartingWith(String startLetters);

    List<Ingredient> findAllByNameInOrderByPrice(List<String> names);

    void deleteAllByName(String name);

    @Query("UPDATE Ingredient AS i " +
            "SET i.price = i.price * 1.1")
    @Modifying
        // always put @Modifying if we type the query
    void updateAllIngredientsPrices();

    @Query("UPDATE Ingredient AS i " +
            "SET i.price = i.price * 0.5 " +
            "WHERE i.name IN :names")
    @Modifying
    void updateAllIngredientsPricesByNameIn(List<String> names);
}
