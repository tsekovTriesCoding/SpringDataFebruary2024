package com.example.football.service.impl;

import com.example.football.models.dto.TeamImportDto;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.football.constants.Messages.INVALID_TEAM;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_TEAM;
import static com.example.football.constants.Paths.TEAMS_PATH;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository,
                           Gson gson,
                           ValidationUtils validationUtils,
                           ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_PATH));
    }

    @Override
    public String importTeams() throws IOException {
        TeamImportDto[] teamImportDtos = this.gson.fromJson(this.readTeamsFileContent(), TeamImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (TeamImportDto teamImportDto : teamImportDtos) {
            boolean isValid = this.validationUtils.isValid(teamImportDto);

            if (this.teamRepository.findByName(teamImportDto.getName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Team team = this.modelMapper.map(teamImportDto, Team.class);

                this.teamRepository.saveAndFlush(team);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_TEAM, team.getName(), team.getFanBase()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_TEAM).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
