package org.example.cardealer.repositories;

import org.example.cardealer.domain.dtos.customer.CustomerSalesDto;
import org.example.cardealer.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select * from car_dealer.customers order by rand () limit 1", nativeQuery = true)
    Optional<Customer> getRandomEntity();

    @Query("select c from Customer as c " +
            "order by c.birthdate, c.isYoungDriver")
    Optional<List<Customer>> getAllOrderByBirthdate();

    @Query("select new org.example.cardealer.domain.dtos.customer.CustomerSalesDto" +
            "(c.name, size(s), sum(p.price)) from Customer c " +
            "join c.sales s " +
            "join s.car ca " +
            "join ca.parts as p " +
            "group by c.name, s.id " +
            "order by sum(p.price) desc, size(s) desc")
    Optional<List<CustomerSalesDto>> getAllByAtLeastOneCarBought();
}
