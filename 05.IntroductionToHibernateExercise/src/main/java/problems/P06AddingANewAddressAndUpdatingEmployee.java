package problems;

import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class P06AddingANewAddressAndUpdatingEmployee {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();
        Address newAddress = new Address();
        newAddress.setText("Vitoshka 15");

        entityManager.persist(newAddress);

        Scanner scanner = new Scanner(System.in);
        String lastname = scanner.nextLine();

        List<Employee> employees = entityManager.createQuery("FROM Employee e WHERE e.lastName = :ln", Employee.class)
                .setParameter("ln", lastname)
                .getResultList();

        employees.forEach(e -> e.setAddress(newAddress));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
