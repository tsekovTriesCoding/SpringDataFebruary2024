package problems;

import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class P08GetEmployeeWithProject {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        int id = new Scanner(System.in).nextInt();

        String employeeInfo = entityManager.createQuery("FROM Employee WHERE id = :employee_id", Employee.class)
                .setParameter("employee_id", id).getSingleResult().employeeWithProjects();

        System.out.println(employeeInfo);

    }
}
