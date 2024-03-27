package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    @Query("select f from Forecast as f " +
            "where f.city.id = :id and f.dayOfWeek = :dayOfWeek")
    Optional<Forecast> findByCityIdAndDayOfWeek(Long id, DayOfWeek dayOfWeek);

    Optional<List<Forecast>> findAllByDayOfWeekAndCityPopulationLessThanOrderByMaxTemperatureDescId(DayOfWeek dayOfWeek, Integer population);
}
