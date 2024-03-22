package com.example.football.service.impl;

import com.example.football.models.dto.PlayerExportDto;
import com.example.football.models.dto.PlayerImportDto;
import com.example.football.models.dto.PlayerWrapperDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.football.constants.Messages.INVALID_PLAYER;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_PLAYER;
import static com.example.football.constants.Paths.PLAYERS_PATH;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             TownRepository townRepository,
                             TeamRepository teamRepository,
                             StatRepository statRepository,
                             ModelMapper modelMapper,
                             ValidationUtils validationUtils,
                             XmlParser xmlParser) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException {
        PlayerWrapperDto playerWrapperDto = this.xmlParser.fromFile(PLAYERS_PATH, PlayerWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (PlayerImportDto player : playerWrapperDto.getPlayers()) {
            boolean isValid = this.validationUtils.isValid(player);

            if (this.playerRepository.findByEmail(player.getEmail()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Player playerToSave = this.modelMapper.map(player, Player.class);

                Town town = this.townRepository.findByName(player.getTown().getName()).get();
                playerToSave.setTown(town);

                Team team = this.teamRepository.findByName(player.getTeam().getName()).get();
                playerToSave.setTeam(team);

                Stat stat = this.statRepository.findById(player.getStat().getId()).get();
                playerToSave.setStat(stat);

                this.playerRepository.saveAndFlush(playerToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_PLAYER, playerToSave.getFirstName(),
                                playerToSave.getLastName(), playerToSave.getPosition()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_PLAYER).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportBestPlayers() {
        String exportInfo = this.playerRepository.findBestPlayers(LocalDate.of(1995, 1, 1),
                        LocalDate.of(2003, 1, 1))
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(PlayerExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));


        return exportInfo.trim();
    }
}
