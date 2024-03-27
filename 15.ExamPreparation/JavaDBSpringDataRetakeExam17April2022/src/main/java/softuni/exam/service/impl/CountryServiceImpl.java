package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_COUNTRY;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_COUNTRY;
import static softuni.exam.constants.Paths.COUNTRIES_PATH;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRIES_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        List<CountryImportDto> countries = Arrays.stream(gson.fromJson(readCountriesFromFile(), CountryImportDto[].class)).toList();

        StringBuilder sb = new StringBuilder();
        for (CountryImportDto country : countries) {

            if (this.countryRepository.findCountryByCountryName(country.getCountryName()).isPresent()) {
                continue;
            }

            boolean isValid = this.validationUtils.isValid(country);

            if (isValid) {
                sb.append(String.format(SUCCESSFULLY_IMPORTED_COUNTRY,
                                country.getCountryName(), country.getCurrency()))
                        .append(System.lineSeparator());

                Country countryToSave = modelMapper.map(country, Country.class);
                this.countryRepository.saveAndFlush(countryToSave);

            } else {
                sb.append(INVALID_COUNTRY).append(System.lineSeparator());
            }

        }

        return sb.toString().trim();
    }
}
