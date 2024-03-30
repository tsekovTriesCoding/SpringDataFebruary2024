package org.example.mvcproject.web;

import org.example.mvcproject.service.employee.EmployeeService;
import org.example.mvcproject.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExportController {
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("export/project-if-finished")
    public String exportAllFinishedProjects(Model model) {
        String finishedProjects = this.projectService.exportFinishedProjects();

        model.addAttribute("projectsIfFinished", finishedProjects);

        return "export/export-project-if-finished";
    }

    @GetMapping("/export/employees-above")
    public String exportEmployeesAboveGivenAge(Model model) {
        String employeesAbove25Years = this.employeeService.exportEmployeesAbove();

        model.addAttribute("employeesAbove", employeesAbove25Years);

        return "export/export-employees-with-age";
    }
}
