package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarExportDto;
import softuni.exam.models.dto.CarImportDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_CAR;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_CAR;
import static softuni.exam.constants.Paths.CARS_PATH;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository,
                          Gson gson, ValidationUtil validationUtil,
                          ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(CARS_PATH));
    }

    @Override
    public String importCars() throws IOException {
        CarImportDto[] carImportDtos = this.gson.fromJson(this.readCarsFileContent(), CarImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (CarImportDto carImportDto : carImportDtos) {
            boolean isValid = this.validationUtil.isValid(carImportDto);

            if (isValid) {
                Car car = this.modelMapper.map(carImportDto, Car.class);

                this.carRepository.saveAndFlush(car);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_CAR, car.getMake(), car.getModel()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_CAR).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        String exportInfo = this.carRepository.findAllOrderByPicturesCountThenByMake()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(Car::toCarExportDto)
                .map(CarExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return exportInfo.trim();
    }
}
