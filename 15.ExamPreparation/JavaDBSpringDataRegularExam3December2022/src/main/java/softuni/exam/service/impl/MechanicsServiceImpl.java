package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicImportDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_MECHANIC;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_MECHANIC;
import static softuni.exam.constants.Paths.MECHANICS_PATH;

@Service
public class MechanicsServiceImpl implements MechanicsService {
    private final MechanicsRepository mechanicsRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public MechanicsServiceImpl(MechanicsRepository mechanicsRepository,
                                Gson gson,
                                ValidationUtils validationUtils,
                                ModelMapper modelMapper) {
        this.mechanicsRepository = mechanicsRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.mechanicsRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANICS_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        MechanicImportDto[] mechanicImportDtos = this.gson.fromJson(this.readMechanicsFromFile(), MechanicImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (MechanicImportDto mechanicImportDto : mechanicImportDtos) {
            boolean isValid = this.validationUtils.isValid(mechanicImportDto);

            if (this.mechanicsRepository.findByEmail(mechanicImportDto.getEmail()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Mechanic mechanic = this.modelMapper.map(mechanicImportDto, Mechanic.class);

                this.mechanicsRepository.saveAndFlush(mechanic);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_MECHANIC, mechanic.getFirstName(), mechanic.getLastName()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_MECHANIC).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
