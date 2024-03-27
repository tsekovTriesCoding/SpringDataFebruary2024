package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.JobExportDto;
import softuni.exam.models.dto.JobImportDto;
import softuni.exam.models.dto.JobsWrapperDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.beans.Transient;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_JOB;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_JOB;
import static softuni.exam.constants.Paths.JOBS_PATH;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository,
                          CompanyRepository companyRepository,
                          ModelMapper modelMapper,
                          ValidationUtil validationUtil,
                          XmlParser xmlParser) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(Path.of(JOBS_PATH));
    }

    @Transactional
    @Override
    public String importJobs() throws IOException, JAXBException {
        JobsWrapperDto jobsWrapperDto = this.xmlParser.fromFile(JOBS_PATH, JobsWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (JobImportDto job : jobsWrapperDto.getJobs()) {
            boolean isValid = this.validationUtil.isValid(job);


            if (isValid) {
                Job jobToSave = this.modelMapper.map(job, Job.class);

                Company company = this.companyRepository.findById(job.getCompanyId()).get();

                jobToSave.setCompany(company);
                company.getJobs().add(jobToSave);

                this.jobRepository.saveAndFlush(jobToSave);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_JOB, jobToSave.getTitle()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_JOB).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String getBestJobs() {
        String exportInfo = this.jobRepository.findAllBySalaryGreaterThanEqualAndHoursAWeekLessThanEqualOrderBySalaryDesc(5000, 30)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(Job::toJobExportDto)
                .map(JobExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return exportInfo.trim();

    }
}
