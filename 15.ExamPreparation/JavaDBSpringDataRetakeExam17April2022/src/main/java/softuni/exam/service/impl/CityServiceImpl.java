package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDto;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_CITY;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_CITY;
import static softuni.exam.constants.Paths.CITIES_PATH;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final CountryRepository countryRepository;

    public CityServiceImpl(CityRepository cityRepository,
                           Gson gson,
                           ValidationUtils validationUtils,
                           ModelMapper modelMapper,
                           CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITIES_PATH));
    }

    @Override
    public String importCities() throws IOException {
        CityImportDto[] cities = gson.fromJson(readCitiesFileContent(), CityImportDto[].class);

        StringBuilder sb = new StringBuilder();

        for (CityImportDto city : cities) {

            if (this.cityRepository.findByCityName(city.getCityName()).isPresent()) {
                sb.append(INVALID_CITY).append(System.lineSeparator());
                continue;
            }

            boolean isValid = this.validationUtils.isValid(city);

            if (isValid) {
                if (this.countryRepository.findCountryById(city.getCountry()).isPresent()) {
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_CITY, city.getCityName(), city.getPopulation()))
                            .append(System.lineSeparator());

                    City cityToSave = modelMapper.map(city, City.class);
                    cityToSave.setCountry(this.countryRepository.findCountryById(city.getCountry()).get());

                    this.cityRepository.saveAndFlush(cityToSave);
                }

            } else {
                sb.append(INVALID_CITY).append(System.lineSeparator());
            }

        }

        return sb.toString().trim();
    }

}
