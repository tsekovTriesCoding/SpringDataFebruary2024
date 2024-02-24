import entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("school");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

//        Student student = new Student("Test");
//        entityManager.persist(student);

        Student found = entityManager.find(Student.class, 1L);
        System.out.println(found.getId() + " " + found.getName());
        entityManager.remove(found);

        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
