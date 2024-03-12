package org.example.jsonprocessingexercise.repositories;

import org.example.jsonprocessingexercise.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from product_shop.users order by rand () limit 1", nativeQuery = true)
    Optional<User> getRandomEntity();

    @Query("select u from User as u " +
            "join u.soldProducts as sp " +
            "where sp.buyerID is not null and size(sp) > 0 " +
            "order by u.lastName, u.firstName")
    Optional<List<User>> findAllBySoldProductsBuyerIDIsNotNullOrderByLastNameFirstName();

    @Query("select u from User as u " +
            "join u.soldProducts as sp " +
            "where size(sp) > 0 and sp.buyerID is not null " +
            "order by size(sp) desc, u.lastName")
    Optional<List<User>> findAllByAtLeastOneSoldProduct();
}

