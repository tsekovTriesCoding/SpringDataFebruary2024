import entities.Diagnose;
import entities.Patient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("If you want to check information about a patient press P");
        System.out.println("If you want to check information about a diagnose press D");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        entityManager.getTransaction().begin();

        if (input.equals("P")) {
            System.out.println("Enter patient last name:");
            String lastName = scanner.nextLine();
            Patient patient = entityManager.createQuery("FROM Patient WHERE lastName = :ln", Patient.class)
                    .setParameter("ln", lastName)
                    .getSingleResult();

            if (patient != null) {
                System.out.println(patient);
            }
        } else if (input.equals("D")) {
            System.out.println("Enter diagnose name:");
            String diagnoseName = scanner.nextLine();

            Diagnose diagnose = entityManager.createQuery("FROM Diagnose WHERE name = :n", Diagnose.class)
                    .setParameter("n", diagnoseName)
                    .getSingleResult();

            if (diagnose != null) {
                System.out.println(diagnose);
            }
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
