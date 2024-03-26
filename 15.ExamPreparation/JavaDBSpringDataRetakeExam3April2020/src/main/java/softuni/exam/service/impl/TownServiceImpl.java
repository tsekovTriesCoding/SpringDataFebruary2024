package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownImportDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_TOWN;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN;
import static softuni.exam.constants.Paths.TOWNS_PATH;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository,
                           Gson gson,
                           ValidationUtil validationUtil,
                           ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
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
    public String importTowns() throws IOException {
        TownImportDto[] townImportDtos = this.gson.fromJson(this.readTownsFileContent(), TownImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (TownImportDto townImportDto : townImportDtos) {
            boolean isValid = this.validationUtil.isValid(townImportDto);

            if (isValid) {
                Town town = this.modelMapper.map(townImportDto, Town.class);

                this.townRepository.saveAndFlush(town);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_TOWN, town.getName(), town.getPopulation()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_TOWN).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
