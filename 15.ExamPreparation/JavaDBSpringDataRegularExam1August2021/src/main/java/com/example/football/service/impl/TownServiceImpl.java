package com.example.football.service.impl;

import com.example.football.models.dto.TownImportDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.football.constants.Messages.INVALID_TOWN;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN;
import static com.example.football.constants.Paths.TOWNS_PATH;


@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository,
                           Gson gson,
                           ValidationUtils validationUtils,
                           ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
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
            boolean isValid = this.validationUtils.isValid(townImportDto);

            if (this.townRepository.findByName(townImportDto.getName()).isPresent()) {
                isValid = false;
            }

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
