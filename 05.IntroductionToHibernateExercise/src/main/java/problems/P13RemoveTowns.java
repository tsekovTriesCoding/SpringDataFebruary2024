package problems;

import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class P13RemoveTowns {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        String townName = scanner.nextLine();

        entityManager.getTransaction().begin();

        List<Address> addressesToDelete = entityManager.createQuery("FROM Address WHERE town.name = :tn", Address.class)
                .setParameter("tn", townName)
                .getResultList();

        addressesToDelete
                .forEach(a -> a.getEmployees()
                        .forEach(e -> e.setAddress(null)));

        addressesToDelete.forEach(entityManager::remove);

        Town town = entityManager.createQuery("FROM Town WHERE name = :tn", Town.class)
                .setParameter("tn", townName)
                .getSingleResult();

        entityManager.remove(town);

        int countOfDeletedAddresses = addressesToDelete.size();

        System.out.printf("%d address%s in %s deleted",
                countOfDeletedAddresses,
                countOfDeletedAddresses == 1 ? "" : "es",
                townName);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
