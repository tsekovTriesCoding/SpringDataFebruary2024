package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_COUNTRY;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_COUNTRY;
import static softuni.exam.constants.Paths.COUNTRIES_PATH;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository,
                              Gson gson,
                              ValidationUtil validationUtil,
                              ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFileContent() throws IOException {
        return Files.readString(Path.of(COUNTRIES_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        CountryImportDto[] countryImportDtos = this.gson.fromJson(this.readCountriesFileContent(), CountryImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (CountryImportDto countryImportDto : countryImportDtos) {
            boolean isValid = this.validationUtil.isValid(countryImportDto);

            if (isValid) {
                Country country = this.modelMapper.map(countryImportDto, Country.class);

                this.countryRepository.saveAndFlush(country);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_COUNTRY, countryImportDto.getName(), country.getCode()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_COUNTRY).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
