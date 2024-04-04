package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoExportDto;
import softuni.exam.models.dto.VolcanoImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Volcano;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.models.enums.Messages.INVALID_VOLCANO;
import static softuni.exam.models.enums.Messages.SUCCESSFULLY_IMPORTED_VOLCANO;
import static softuni.exam.models.enums.Paths.VOLCANOES_PATH;

@Service
public class VolcanoServiceImpl implements VolcanoService {
    private final VolcanoRepository volcanoRepository;
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public VolcanoServiceImpl(VolcanoRepository volcanoRepository,
                              CountryRepository countryRepository,
                              Gson gson,
                              ValidationUtil validationUtil,
                              ModelMapper modelMapper) {
        this.volcanoRepository = volcanoRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(VOLCANOES_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        VolcanoImportDto[] volcanoImportDtos = this.gson.fromJson(this.readVolcanoesFileContent(), VolcanoImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (VolcanoImportDto volcanoImportDto : volcanoImportDtos) {
            boolean isValid = this.validationUtil.isValid(volcanoImportDto);

            if (this.volcanoRepository.findByName(volcanoImportDto.getName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Volcano volcano = this.modelMapper.map(volcanoImportDto, Volcano.class);

                if (this.countryRepository.findById(volcanoImportDto.getCountry()).isPresent()) {
                    Country country = this.countryRepository.findById(volcanoImportDto.getCountry()).get();

                    volcano.setCountry(country);
                }

                this.volcanoRepository.saveAndFlush(volcano);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_VOLCANO, volcano.getName(), volcano.getVolcanoType()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_VOLCANO).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportVolcanoes() {
        String exportInfo = this.volcanoRepository.findAllByIsActiveTrueAndElevationGreaterThanAndLastEruptionIsNotNullOrderByElevationDesc(3000)
                .orElseThrow(NoSuchElementException::new)
                .stream().map(volcano -> this.modelMapper.map(volcano, VolcanoExportDto.class))
                .map(VolcanoExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return exportInfo.trim();
    }
}