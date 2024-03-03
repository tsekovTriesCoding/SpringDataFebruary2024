import entities.Car;
import entities.Plane;
import relation.entities.PlateNumber;
import entities.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("relations");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        PlateNumber plateNumber = new PlateNumber("2838hdgt3");
        Vehicle car = new Car("BMW", "diesel", 5, plateNumber);
        Vehicle plane = new Plane("Boeing", 312);

        entityManager.persist(plateNumber);
        entityManager.persist(car);
        entityManager.persist(plane);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
