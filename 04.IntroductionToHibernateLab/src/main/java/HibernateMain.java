import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateMain {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

//        Student example = new Student();
//        example.setName("Test");
//        session.persist(example);

        Student student = session.get(Student.class, 1L);
        System.out.println(student.getId() + " " + student.getName());

                                                  // using HBL, not SQl
        List<Student> students = session.createQuery("FROM Student WHERE name = 'Test'", Student.class).list();
        for (Student s : students) {
            System.out.println(s.getId() + " " + s.getName());
        }

        session.getTransaction();
        session.close();
    }
}
