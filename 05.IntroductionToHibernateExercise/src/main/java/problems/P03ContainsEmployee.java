package problems;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class P03ContainsEmployee {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        String[] name = scanner.nextLine().split(" ");

        Long matchesCount = entityManager.createQuery("SELECT count(e) FROM Employee e WHERE e.firstName = :fn AND e.lastName = :ln",
                        Long.class)
                .setParameter("fn", name[0])
                .setParameter("ln", name[1])
                .getSingleResult();

        if (matchesCount > 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

        entityManager.close();

    }
}
