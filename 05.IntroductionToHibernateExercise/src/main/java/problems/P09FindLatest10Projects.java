package problems;

import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;

public class P09FindLatest10Projects {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        entityManager.createQuery("FROM Project ORDER BY startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(e -> System.out.println(e.toString()));

    }
}
