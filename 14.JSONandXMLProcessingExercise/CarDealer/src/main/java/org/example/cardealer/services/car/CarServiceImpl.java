package org.example.cardealer.services.car;

import org.example.cardealer.domain.dtos.car.CarBasicInfoDto;
import org.example.cardealer.domain.dtos.car.CarWithAllPartsDto;
import org.example.cardealer.domain.dtos.car.wrappers.CarsByMakeWrapperDto;
import org.example.cardealer.domain.dtos.car.wrappers.CarsWithPartsWrapperDto;
import org.example.cardealer.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.example.cardealer.constants.Paths.*;
import static org.example.cardealer.constants.Utils.*;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<CarBasicInfoDto> getAllByMakeOrderByModelTravelledDistanceDesc(String make) throws IOException, JAXBException {
        List<CarBasicInfoDto> toyotaCars = this.carRepository.getAllByMakeOrderByModelTravelledDistanceDesc(make)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(car -> MODEL_MAPPER.map(car, CarBasicInfoDto.class))
                .toList();

        CarsByMakeWrapperDto carsByMakeWrapperDto = new CarsByMakeWrapperDto(toyotaCars);

        writeXmlOnToFile(carsByMakeWrapperDto, TOYOTA_CARS_XML_PATH);

//        writeJsonOnToFile(toyotaCars, TOYOTA_CARS_JSON_PATH);

        return toyotaCars;
    }

    @Override
    public List<CarWithAllPartsDto> getAllCars() throws IOException, JAXBException {
        List<CarWithAllPartsDto> cars = this.carRepository.getAll().orElseThrow(NoSuchElementException::new)
                .stream()
                .map(car -> MODEL_MAPPER.map(car, CarWithAllPartsDto.class))
                .toList();

        writeJsonOnToFile(cars, CARS_WITH_PARTS_JSON_PATH);

        CarsWithPartsWrapperDto carsWithPartsWrapperDto = new CarsWithPartsWrapperDto(cars);

        writeXmlOnToFile(carsWithPartsWrapperDto, CARS_WITH_PARTS_XML_PATH);

        return cars;
    }
}
