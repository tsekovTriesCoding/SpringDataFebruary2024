package org.example.mvcproject.web;

import org.example.mvcproject.service.company.CompanyService;
import org.example.mvcproject.service.employee.EmployeeService;
import org.example.mvcproject.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public HomeController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        boolean areImported = this.companyService.areImported() &&
                this.projectService.areImported() &&
                this.employeeService.areImported();

        model.addAttribute("areImported", areImported);

        return "home";
    }
}
