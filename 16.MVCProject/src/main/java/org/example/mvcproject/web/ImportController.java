package org.example.mvcproject.web;

import org.example.mvcproject.service.company.CompanyService;
import org.example.mvcproject.service.employee.EmployeeService;
import org.example.mvcproject.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.example.mvcproject.constants.Paths.*;

@Controller
public class ImportController {
    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ImportController(CompanyService companyService,
                            ProjectService projectService,
                            EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/import/xml")
    public String index(Model model) {
        boolean[] areImported = {this.companyService.areImported(),
                this.projectService.areImported(),
                this.employeeService.areImported()};

        model.addAttribute("areImported", areImported);
        return "xml/import-xml";
    }

    @GetMapping("/import/companies")
    public String importCompanies(Model model) throws IOException {
        List<String> xmlContent = Files.readAllLines(Path.of(COMPANIES_PATH));

        model.addAttribute("companies", String.join(System.lineSeparator(), xmlContent));
        return "xml/import-companies";
    }

    @PostMapping("/import/companies")
    public String doImportCompanies() throws JAXBException {
        this.companyService.importCompanies();

        return ("redirect:/import/xml");
    }

    @GetMapping("/import/projects")
    public String importProjects(Model model) throws IOException {
        List<String> xmlContent = Files.readAllLines(Path.of(PROJECTS_PATH));

        model.addAttribute("projects", String.join(System.lineSeparator(), xmlContent));
        return "xml/import-projects";
    }

    @PostMapping("/import/projects")
    public String doImportProjects() throws JAXBException {
        this.projectService.importProjects();

        return ("redirect:/import/xml");
    }

    @GetMapping("/import/employees")
    public String importEmployees(Model model) throws IOException {
        List<String> xmlContent = Files.readAllLines(Path.of(EMPLOYEES_PATH));

        model.addAttribute("employees", String.join(System.lineSeparator(), xmlContent));
        return "xml/import-employees";
    }

    @PostMapping("/import/employees")
    public String doImportEmployees() throws JAXBException {
        this.employeeService.importEmployees();
        return ("redirect:/import/xml");
    }
}

