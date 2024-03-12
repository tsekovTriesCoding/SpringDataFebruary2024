package org.example.jsonprocessingexercise.repositories;

import org.example.jsonprocessingexercise.domain.dtos.category.CategorySummaryDto;
import org.example.jsonprocessingexercise.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from product_shop.categories order by rand () limit 1", nativeQuery = true)
    Optional<Category> getRandomEntity();

    @Query("select new org.example.jsonprocessingexercise.domain.dtos.category.CategorySummaryDto" +
            "(c.name, count(p.id), avg(p.price), sum(p.price)) " +
            "from Product as p " +
            "join p.categories as c " +
            "group by c.id " +
            "order by count(p.id)")
    Optional<List<CategorySummaryDto>> getCategorySummary();

}


