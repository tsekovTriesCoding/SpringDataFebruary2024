package org.example.cardealer.repositories;

import org.example.cardealer.domain.entities.PartSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<PartSupplier, Long> {
    @Query(value = "select * from car_dealer.part_suppliers order by rand () limit 1", nativeQuery = true)
    Optional<PartSupplier> getRandomEntity();

    Optional<List<PartSupplier>> getAllByIsImporterIsFalse();
}
