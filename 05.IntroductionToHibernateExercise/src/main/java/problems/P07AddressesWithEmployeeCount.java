package problems;

import entities.Address;

import javax.persistence.EntityManager;

public class P07AddressesWithEmployeeCount {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        entityManager.createQuery("FROM Address ORDER BY employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(System.out::println);

    }
}
