package problems;

import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class P10IncreaseSalaries {
    private static final List<String> DEPARTMENTS_NAMES =
            List.of("Engineering",
                    "Tool Design",
                    "Marketing",
                    "Information Services");

    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        final List<Employee> employees = entityManager
                .createQuery("FROM Employee WHERE department.name in (:departments)", Employee.class)
                .setParameter("departments", DEPARTMENTS_NAMES)
                .getResultList();

        employees.forEach(employee -> employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12))));

        entityManager.getTransaction().commit();
        entityManager.close();

        employees.forEach(employee -> System.out.printf("%s %s (%s)%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getSalary()));
    }
}
