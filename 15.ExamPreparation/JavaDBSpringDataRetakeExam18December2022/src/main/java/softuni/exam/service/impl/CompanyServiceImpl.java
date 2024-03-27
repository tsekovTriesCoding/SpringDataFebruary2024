package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CompaniesWrapperDto;
import softuni.exam.models.dto.CompanyImportDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_COMPANY;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_COMPANY;
import static softuni.exam.constants.Paths.COMPANIES_PATH;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              CountryRepository countryRepository,
                              ModelMapper modelMapper,
                              ValidationUtil validationUtil,
                              XmlParser xmlParser) {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(COMPANIES_PATH));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        CompaniesWrapperDto companiesWrapperDto = this.xmlParser.fromFile(COMPANIES_PATH, CompaniesWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (CompanyImportDto company : companiesWrapperDto.getCompanies()) {
            boolean isValid = this.validationUtil.isValid(company);

            if (this.companyRepository.findByName(company.getCompanyName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Company companyToSave = this.modelMapper.map(company, Company.class);

                Country country = this.countryRepository.findById(company.getCountryId()).get();

                companyToSave.setCountry(country);

                this.companyRepository.saveAndFlush(companyToSave);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_COMPANY, companyToSave.getName(), companyToSave.getCountry().getId()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_COMPANY).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
