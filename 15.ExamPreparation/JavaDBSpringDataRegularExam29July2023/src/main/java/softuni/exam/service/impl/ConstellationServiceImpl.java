package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softuni.exam.models.dto.ConstellationImportDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_CONSTELLATION;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_CONSTELLATION;
import static softuni.exam.constants.Paths.CONSTELLATIONS_PATH;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository,
                                    Gson gson,
                                    ValidationUtils validationUtils,
                                    ModelMapper modelMapper) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATIONS_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        final ConstellationImportDto[] constellationImportDtos = this.gson.fromJson(readConstellationsFromFile(), ConstellationImportDto[].class);

        final StringBuilder sb = new StringBuilder();
        for (ConstellationImportDto constellationImportDto : constellationImportDtos) {
            boolean isValid = this.validationUtils.isValid(constellationImportDto);

            if (this.constellationRepository.findByName(constellationImportDto.getName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                final Constellation constellation = this.modelMapper.map(constellationImportDto, Constellation.class);

                this.constellationRepository.saveAndFlush(constellation);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_CONSTELLATION, constellation.getName(), constellation.getDescription()))
                        .append(System.lineSeparator());

            } else {
                sb.append(INVALID_CONSTELLATION).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
