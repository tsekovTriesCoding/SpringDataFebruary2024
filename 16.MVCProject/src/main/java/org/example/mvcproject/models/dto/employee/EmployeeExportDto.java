package org.example.mvcproject.models.dto.employee;

public class EmployeeExportDto {
    private String firstName;
    private String lastName;
    private Integer age;
    private String projectName;

    public EmployeeExportDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "Name: " + this.getFullName() + System.lineSeparator() +
                "   age: " + this.age + System.lineSeparator() +
                "   Project name: " + this.projectName;
    }

    private String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
