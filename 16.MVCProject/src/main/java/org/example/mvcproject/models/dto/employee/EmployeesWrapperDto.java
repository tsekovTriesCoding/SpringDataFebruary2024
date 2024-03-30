package org.example.mvcproject.models.dto.employee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeesWrapperDto {
    @XmlElement(name = "employee")
    private List<EmployeeImportDto> employees;

    public EmployeesWrapperDto() {
    }

    public List<EmployeeImportDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeImportDto> employees) {
        this.employees = employees;
    }
}
