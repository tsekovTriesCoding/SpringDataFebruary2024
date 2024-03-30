package org.example.mvcproject.service.employee;

import org.example.mvcproject.models.dto.employee.EmployeeExportDto;
import org.example.mvcproject.models.dto.employee.EmployeeImportDto;
import org.example.mvcproject.models.dto.employee.EmployeesWrapperDto;
import org.example.mvcproject.models.entity.Employee;
import org.example.mvcproject.models.entity.Project;
import org.example.mvcproject.repository.EmployeeRepository;
import org.example.mvcproject.repository.ProjectRepository;
import org.example.mvcproject.utils.ValidationUtil;
import org.example.mvcproject.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.example.mvcproject.constants.Paths.EMPLOYEES_PATH;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ProjectRepository projectRepository,
                               ModelMapper modelMapper,
                               ValidationUtil validationUtil,
                               XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public void importEmployees() throws JAXBException {
        EmployeesWrapperDto employeesWrapperDto = this.xmlParser.fromFile(EMPLOYEES_PATH, EmployeesWrapperDto.class);

        employeesWrapperDto.getEmployees()
                .stream()
                .filter(employeeImportDto -> this.validationUtil.isValid(employeeImportDto) &&
                        this.projectRepository.findByName(employeeImportDto.getProject().getName()).isPresent())
                .map(this::toEntity)
                .forEach(this.employeeRepository::saveAndFlush);
    }

    @Override
    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String exportEmployeesAbove() {
        String exportInfo = this.employeeRepository.findAllByAgeGreaterThan(25)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(employee -> this.modelMapper.map(employee, EmployeeExportDto.class))
                .map(EmployeeExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return exportInfo.trim();
    }

    private Employee toEntity(EmployeeImportDto employeeImportDto) {
        Employee employee = this.modelMapper.map(employeeImportDto, Employee.class);

        Project project = this.projectRepository.findByName(employeeImportDto.getProject().getName())
                .orElseThrow(NoSuchElementException::new);

        employee.setProject(project);

        return employee;
    }
}
