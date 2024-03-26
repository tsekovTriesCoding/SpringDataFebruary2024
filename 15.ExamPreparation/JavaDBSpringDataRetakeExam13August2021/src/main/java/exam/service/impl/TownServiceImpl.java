package exam.service.impl;

import exam.model.dto.TownImportDto;
import exam.model.dto.TownsWrapperDto;
import exam.model.entity.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static exam.constants.Messages.INVALID_TOWN;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN;
import static exam.constants.Paths.TOWNS_PATH;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public TownServiceImpl(TownRepository townRepository,
                           ModelMapper modelMapper,
                           ValidationUtils validationUtils,
                           XmlParser xmlParser) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_PATH));
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        TownsWrapperDto townsWrapperDto = this.xmlParser.fromFile(TOWNS_PATH, TownsWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (TownImportDto town : townsWrapperDto.getTowns()) {
            boolean isValid = this.validationUtils.isValid(town);

            if (isValid) {
                Town townToSave = this.modelMapper.map(town, Town.class);

                this.townRepository.saveAndFlush(townToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_TOWN, townToSave.getName()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_TOWN).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
