package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanologistsImportDto;
import softuni.exam.models.dto.VolcanologistsWrapperDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.models.enums.Messages.INVALID_VOLCANOLOGISTS;
import static softuni.exam.models.enums.Messages.SUCCESSFULLY_IMPORTED_VOLCANOLOGIST;
import static softuni.exam.models.enums.Paths.VOLCANOLOGISTS_PATH;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {
    private final VolcanologistRepository volcanologistRepository;
    private final VolcanoRepository volcanoRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository,
                                    VolcanoRepository volcanoRepository,
                                    ModelMapper modelMapper,
                                    ValidationUtil validationUtil,
                                    XmlParser xmlParser) {
        this.volcanologistRepository = volcanologistRepository;
        this.volcanoRepository = volcanoRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(VOLCANOLOGISTS_PATH));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        VolcanologistsWrapperDto volcanologistsWrapperDto = this.xmlParser.fromFile(VOLCANOLOGISTS_PATH, VolcanologistsWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (VolcanologistsImportDto volcanologist : volcanologistsWrapperDto.getVolcanologists()) {
            boolean isValid = this.validationUtil.isValid(volcanologist);

            if (this.volcanologistRepository.findByFirstNameAndLastName(volcanologist.getFirstName(),
                    volcanologist.getLastName()).isPresent() || this.volcanoRepository.findById(volcanologist.getVolcano()).isEmpty()) {
                isValid = false;
            }

            if (isValid) {
                Volcanologist volcanologistToSave = this.modelMapper.map(volcanologist, Volcanologist.class);

                Volcano volcano = this.volcanoRepository.findById(volcanologist.getVolcano()).get();

                volcanologistToSave.setVolcano(volcano);

                this.volcanologistRepository.saveAndFlush(volcanologistToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_VOLCANOLOGIST, volcanologistToSave.getFullName()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_VOLCANOLOGISTS).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}