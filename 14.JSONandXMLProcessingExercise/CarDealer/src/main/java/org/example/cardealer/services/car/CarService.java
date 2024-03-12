package org.example.cardealer.services.car;

import org.example.cardealer.domain.dtos.car.CarBasicInfoDto;
import org.example.cardealer.domain.dtos.car.CarWithAllPartsDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface CarService {
    List<CarBasicInfoDto> getAllByMakeOrderByModelTravelledDistanceDesc(String make) throws IOException, JAXBException;

    List<CarWithAllPartsDto> getAllCars() throws IOException, JAXBException;
}
