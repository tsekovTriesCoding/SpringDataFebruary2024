package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.StarExportDto;
import softuni.exam.models.dto.StarImportDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_STAR;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_STAR;
import static softuni.exam.constants.Paths.STARS_PATH;

@Service
public class StarServiceImpl implements StarService {
    private final StarRepository starRepository;
    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public StarServiceImpl(StarRepository starRepository, ConstellationRepository constellationRepository, Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(STARS_PATH));
    }

    @Override
    public String importStars() throws IOException {
        final StarImportDto[] starImportStos = this.gson.fromJson(this.readStarsFileContent(), StarImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (StarImportDto starImportDto : starImportStos) {
            boolean isValid = this.validationUtils.isValid(starImportDto);

            if (this.starRepository.findByName(starImportDto.getName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Star star = this.modelMapper.map(starImportDto, Star.class);
                final Constellation constellation = this.constellationRepository.findById(starImportDto.getConstellation()).get();

                star.setConstellation(constellation);

                this.starRepository.saveAndFlush(star);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_STAR, star.getName(), star.getLightYears()))
                        .append(System.lineSeparator());

            } else {
                sb.append(INVALID_STAR).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportStars() {
        List<Star> stars = this.starRepository.findAllByStarTypeAndObserversIsEmptyOrderByLightYears(StarType.RED_GIANT)
                .orElseThrow(NoSuchElementException::new);

        String starsInfoExport = stars.stream()
                .map(star -> this.modelMapper.map(star, StarExportDto.class))
                .map(StarExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return starsInfoExport.trim();
    }
}
