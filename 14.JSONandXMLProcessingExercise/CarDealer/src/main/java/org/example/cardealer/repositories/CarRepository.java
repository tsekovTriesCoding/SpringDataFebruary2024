package org.example.cardealer.repositories;

import org.example.cardealer.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(value = "select * from car_dealer.cars order by rand () limit 1", nativeQuery = true)
    Optional<Car> getRandomEntity();

    @Query("select c from  Car as c " +
            "where c.make = :make " +
            "order by c.model, c.travelledDistance desc")
    Optional<List<Car>> getAllByMakeOrderByModelTravelledDistanceDesc(String make);

    @Query("select c from Car as c")
    Optional<List<Car>> getAll();

}
