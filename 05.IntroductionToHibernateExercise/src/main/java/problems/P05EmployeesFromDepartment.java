package problems;

import entities.Employee;

import javax.persistence.EntityManager;

public class P05EmployeesFromDepartment {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();

        entityManager.createQuery("FROM Employee e WHERE e.department.name = 'Research and Development' " +
                        "ORDER BY e.salary, e.id", Employee.class)
                .getResultList()
                .forEach(e -> System.out.println(e.employeesFromDepartmentInfo()));
    }
}
