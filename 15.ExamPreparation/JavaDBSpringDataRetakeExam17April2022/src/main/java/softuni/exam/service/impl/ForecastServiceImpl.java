package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastExportDto;
import softuni.exam.models.dto.ForecastImportDto;
import softuni.exam.models.dto.ForecastsWrapperDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_FORECAST;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_FORECAST;
import static softuni.exam.constants.Paths.CITIES_PATH;
import static softuni.exam.constants.Paths.FORECASTS_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository,
                               CityRepository cityRepository,
                               XmlParser xmlParser,
                               ValidationUtils validationUtils,
                               ModelMapper modelMapper) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECASTS_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        List<ForecastImportDto> forecasts = this.xmlParser.fromFile(Path.of(FORECASTS_PATH).toFile(), ForecastsWrapperDto.class)
                .getForecasts();

        StringBuilder sb = new StringBuilder();
        for (ForecastImportDto forecast : forecasts) {
            if (this.validationUtils.isValid(forecast)) {
                if (this.forecastRepository.findByCityIdAndDayOfWeek(forecast.getCity(), forecast.getDayOfWeek()).isPresent()) {
                    sb.append(INVALID_FORECAST).append(System.lineSeparator());
                    continue;
                }

                sb.append(String.format(SUCCESSFULLY_IMPORTED_FORECAST, forecast.getDayOfWeek(), forecast.getMaxTemperature()))
                        .append(System.lineSeparator());

                Forecast forecastToSave = this.modelMapper.map(forecast, Forecast.class);
                forecastToSave.setCity(cityRepository.findById(forecast.getCity()).get());
                this.forecastRepository.saveAndFlush(forecastToSave);
            } else {
                sb.append(INVALID_FORECAST).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportForecasts() {
        List<Forecast> forecasts = this.forecastRepository.findAllByDayOfWeekAndCityPopulationLessThanOrderByMaxTemperatureDescId(DayOfWeek.SUNDAY, 150000)
                .orElseThrow(NoSuchElementException::new);

        String output = forecasts
                .stream()
                .map(forecast -> modelMapper.map(forecast, ForecastExportDto.class))
                .map(ForecastExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return output.trim();
    }
}
