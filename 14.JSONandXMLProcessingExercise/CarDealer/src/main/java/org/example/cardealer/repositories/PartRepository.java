package org.example.cardealer.repositories;

import org.example.cardealer.domain.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    @Query(value = "select * from car_dealer.parts order by rand () limit 1", nativeQuery = true)
    Optional<Part> getRandomEntity();
}
