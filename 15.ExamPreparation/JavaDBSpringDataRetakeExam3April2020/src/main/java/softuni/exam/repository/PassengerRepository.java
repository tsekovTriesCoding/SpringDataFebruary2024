package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.PassengerExportDto;
import softuni.exam.models.entity.Passenger;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByEmail(String email);

    @Query("select p from Passenger p " +
            "join Ticket as t on t.passenger.id = p.id " +
            "group by p.id " +
            "order by count(t.id) desc, p.email")
    Optional<List<Passenger>> findAllPassengers();
}
