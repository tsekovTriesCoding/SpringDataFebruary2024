package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarImportDto;
import softuni.exam.models.dto.CarsWrapperDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_CAR;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_CAR;

@Service
public class CarsServiceImpl implements CarsService {
    private static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";
    private final CarsRepository carsRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public CarsServiceImpl(CarsRepository carsRepository,
                           ModelMapper modelMapper,
                           ValidationUtils validationUtils,
                           XmlParser xmlParser) {
        this.carsRepository = carsRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.carsRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        CarsWrapperDto carsWrapperDto = this.xmlParser.fromFile(CARS_FILE_PATH, CarsWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (CarImportDto carImportDto : carsWrapperDto.getCars()) {
            boolean isValid = this.validationUtils.isValid(carImportDto);

            if (this.carsRepository.findByPlateNumber(carImportDto.getPlateNumber()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Car car = this.modelMapper.map(carImportDto, Car.class);

                this.carsRepository.saveAndFlush(car);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_CAR, car.getCarMake(), car.getCarModel()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_CAR).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
