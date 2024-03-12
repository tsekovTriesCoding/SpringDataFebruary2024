package org.example.cardealer.repositories;

import org.example.cardealer.domain.dtos.sale.SaleDto;
import org.example.cardealer.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("select s from Sale as s")
    Optional<List<Sale>> findAllSales();
}
