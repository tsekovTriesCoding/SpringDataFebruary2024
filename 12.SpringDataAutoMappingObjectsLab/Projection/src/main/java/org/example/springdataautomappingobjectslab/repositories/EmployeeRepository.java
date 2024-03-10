package org.example.springdataautomappingobjectslab.repositories;

import org.example.springdataautomappingobjectslab.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(long id);

    Optional<List<Employee>> findAllByManagerId(long id);

    @Query("select e from Employee as e where year(e.birthday) < :year " +
            "order by e.salary desc")
    Optional<List<Employee>> findAllByBirthdayBeforeOrderBySalaryDesc(int year);

}
