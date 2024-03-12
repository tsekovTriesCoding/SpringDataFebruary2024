package org.example.jsonprocessingexercise.repositories;

import org.example.jsonprocessingexercise.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findAllByPriceBetweenAndBuyerIDIsNullOrderByPrice(BigDecimal low, BigDecimal high);

}
