package problems;

import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class P11FindEmployeesByFirstName {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        String pattern = scanner.nextLine();

        entityManager.createQuery("FROM Employee WHERE firstName LIKE CONCAT(:p, '%')", Employee.class)
                .setParameter("p", pattern)
                .getResultList()
                .forEach(Employee::printEmployeeByFirstname);

    }
}
