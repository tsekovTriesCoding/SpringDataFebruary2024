package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PlaneImportDto;
import softuni.exam.models.dto.PlanesWrapperDto;
import softuni.exam.models.entity.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_PLANE;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_PLANE;
import static softuni.exam.constants.Paths.PLANES_PATH;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository,
                            ModelMapper modelMapper,
                            ValidationUtil validationUtils,
                            XmlParser xmlParser) {
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PLANES_PATH));
    }

    @Override
    public String importPlanes() throws JAXBException {
        PlanesWrapperDto planesWrapperDto = this.xmlParser.fromFile(PLANES_PATH, PlanesWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (PlaneImportDto plane : planesWrapperDto.getPlanes()) {
            boolean isValid = this.validationUtils.isValid(plane);

            if (isValid) {
                Plane planeToSave = this.modelMapper.map(plane, Plane.class);

                this.planeRepository.saveAndFlush(planeToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_PLANE, planeToSave.getRegisterNumber()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_PLANE).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
