package org.example.mvcproject.service.company;

import org.example.mvcproject.models.dto.company.CompaniesWrapperDto;
import org.example.mvcproject.models.entity.Company;
import org.example.mvcproject.repository.CompanyRepository;
import org.example.mvcproject.utils.ValidationUtil;
import org.example.mvcproject.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

import static org.example.mvcproject.constants.Paths.COMPANIES_PATH;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              ModelMapper modelMapper,
                              ValidationUtil validationUtil,
                              XmlParser xmlParser) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public void importCompanies() throws JAXBException {
        CompaniesWrapperDto companiesWrapperDto = this.xmlParser.fromFile(COMPANIES_PATH, CompaniesWrapperDto.class);

        companiesWrapperDto.getCompanies()
                .stream()
                .filter(this.validationUtil::isValid)
                .map(companyImportDto -> this.modelMapper.map(companyImportDto, Company.class))
                .forEach(this.companyRepository::saveAndFlush);
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }
}
