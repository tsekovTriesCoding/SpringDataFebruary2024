package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PassengerExportDto;
import softuni.exam.models.dto.PassengerImportDto;
import softuni.exam.models.entity.Passenger;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_PASSENGER;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_PASSENGER;
import static softuni.exam.constants.Paths.PASSENGERS_PATH;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                TownRepository townRepository,
                                Gson gson,
                                ValidationUtil validationUtil,
                                ModelMapper modelMapper) {
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGERS_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        PassengerImportDto[] passengerImportDtos = this.gson.fromJson(this.readPassengersFileContent(), PassengerImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (PassengerImportDto passengerImportDto : passengerImportDtos) {
            boolean isValid = this.validationUtil.isValid(passengerImportDto);

            if (isValid) {
                Passenger passenger = this.modelMapper.map(passengerImportDto, Passenger.class);

                if (this.townRepository.findByName(passengerImportDto.getTown()).isPresent()) {
                    Town town = this.townRepository.findByName(passengerImportDto.getTown()).get();

                    passenger.setTown(town);
                }

                this.passengerRepository.saveAndFlush(passenger);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_PASSENGER, passenger.getLastName(), passenger.getEmail()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_PASSENGER).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        String exportInfo = this.passengerRepository.findAllPassengers()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(Passenger::toPassengerExportDto)
                .map(PassengerExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return exportInfo.trim();
    }
}
