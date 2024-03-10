package org.example.springdataadvancedqueryinglab.repositories;

import org.example.springdataadvancedqueryinglab.enitites.Shampoo;
import org.example.springdataadvancedqueryinglab.enitites.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long labelId);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countAllByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE i.name IN :names")
    List<Shampoo> findAllByIngredientsIn(List<String> names);

    //  @Query("SELECT s FROM Shampoo AS s " +
    //            "WHERE s.ingredients.size < :count") is not working... throw exception???
    @Query("SELECT s FROM Shampoo AS s " +
            "WHERE SIZE(s.ingredients) < :count")
    List<Shampoo> findAllByIngredientsCountLessThan(int count);
}
