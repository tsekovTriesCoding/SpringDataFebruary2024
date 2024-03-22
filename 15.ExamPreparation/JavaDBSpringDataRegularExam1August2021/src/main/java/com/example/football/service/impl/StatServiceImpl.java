package com.example.football.service.impl;

import com.example.football.models.dto.StatImportDto;
import com.example.football.models.dto.StatsWrapperDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.football.constants.Messages.INVALID_STAT;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_STAT;
import static com.example.football.constants.Paths.STATS_PATH;

@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;


    @Autowired
    public StatServiceImpl(StatRepository statRepository,
                           ModelMapper modelMapper,
                           ValidationUtils validationUtils,
                           XmlParser xmlParser) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_PATH));
    }

    @Override
    public String importStats() throws JAXBException {
        StatsWrapperDto statsWrapperDto = this.xmlParser.fromFile(STATS_PATH, StatsWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (StatImportDto stat : statsWrapperDto.getStats()) {
            boolean isValid = this.validationUtils.isValid(stat);

            if (this.statRepository.findByPassingAndShootingAndEndurance(stat.getPassing(), stat.getShooting(), stat.getEndurance()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Stat statToSave = this.modelMapper.map(stat, Stat.class);

                this.statRepository.saveAndFlush(statToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_STAT, statToSave.getPassing(), statToSave.getShooting(), statToSave.getEndurance()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_STAT).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
