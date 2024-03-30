package org.example.mvcproject.service.project;

import org.example.mvcproject.models.dto.project.ProjectExportDto;
import org.example.mvcproject.models.dto.project.ProjectImportDto;
import org.example.mvcproject.models.dto.project.ProjectsWrapperDto;
import org.example.mvcproject.models.entity.Company;
import org.example.mvcproject.models.entity.Project;
import org.example.mvcproject.repository.CompanyRepository;
import org.example.mvcproject.repository.ProjectRepository;
import org.example.mvcproject.utils.ValidationUtil;
import org.example.mvcproject.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.example.mvcproject.constants.Paths.PROJECTS_PATH;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              CompanyRepository companyRepository,
                              ModelMapper modelMapper,
                              ValidationUtil validationUtil,
                              XmlParser xmlParser) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public void importProjects() throws JAXBException {
        ProjectsWrapperDto projectsWrapperDto = this.xmlParser.fromFile(PROJECTS_PATH, ProjectsWrapperDto.class);

        projectsWrapperDto.getProjects()
                .stream()
                .filter(this.validationUtil::isValid)
                .map(this::toEntity)
                .forEach(this.projectRepository::saveAndFlush);
    }

    @Override
    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    private Project toEntity(ProjectImportDto projectImportDto) {
        Project project = this.modelMapper.map(projectImportDto, Project.class);

        Company company = this.companyRepository.findByName(projectImportDto.getCompany().getName())
                .orElseThrow(NoSuchElementException::new);

        project.setCompany(company);

        return project;
    }

    public String exportFinishedProjects() {
        String exportInfo = this.projectRepository.findAllByIsFinishedIsTrue()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(project -> this.modelMapper.map(project, ProjectExportDto.class))
                .map(ProjectExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return exportInfo.trim();
    }
}
