package org.example.springdataautomappingobjectslab.repositories;

import org.example.springdataautomappingobjectslab.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
