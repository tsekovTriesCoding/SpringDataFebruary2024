package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerImportDto;
import softuni.exam.models.dto.AstronomersWrapperDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_ASTRONOMER;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_ASTRONOMER;
import static softuni.exam.constants.Paths.ASTRONOMERS_PATH;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    private final AstronomerRepository astronomerRepository;
    private final StarRepository starRepository;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public AstronomerServiceImpl(AstronomerRepository astronomerRepository,
                                 StarRepository starRepository,
                                 XmlParser xmlParser,
                                 ValidationUtils validationUtils,
                                 ModelMapper modelMapper) {
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRONOMERS_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        List<AstronomerImportDto> astronomers = this.xmlParser.fromFile(Path.of(ASTRONOMERS_PATH).toFile(), AstronomersWrapperDto.class)
                .getAstronomers();

        StringBuilder sb = new StringBuilder();
        for (AstronomerImportDto astronomer : astronomers) {
            boolean isValid = this.validationUtils.isValid(astronomer);

            if (this.astronomerRepository.findByFirstNameAndLastName(astronomer.getFirstName(), astronomer.getLastName()).isPresent() ||
                    this.starRepository.findById(astronomer.getObservingStarId()).isEmpty()) {
                isValid = false;
            }

            if (isValid) {
                Astronomer astronomerToSave = this.modelMapper.map(astronomer, Astronomer.class);

                astronomerToSave.setStar(this.starRepository.findById(astronomer.getObservingStarId()).get());
                this.astronomerRepository.saveAndFlush(astronomerToSave);

                String fullName = astronomerToSave.getFirstName() + " " + astronomerToSave.getLastName();
                sb.append(String.format(SUCCESSFULLY_IMPORTED_ASTRONOMER, fullName, astronomerToSave.getAverageObservationHours()))
                        .append(System.lineSeparator());

            } else {
                sb.append(INVALID_ASTRONOMER)
                        .append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
