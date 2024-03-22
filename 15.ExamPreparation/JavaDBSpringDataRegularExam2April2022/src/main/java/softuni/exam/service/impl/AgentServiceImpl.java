package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImportDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_AGENT;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_AGENT;
import static softuni.exam.constants.Paths.AGENTS_PATH;

@Service
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository,
                            TownRepository townRepository,
                            Gson gson,
                            ValidationUtils validationUtils,
                            ModelMapper modelMapper) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {

        return Files.readString(Path.of(AGENTS_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        AgentImportDto[] agentImportDtos = this.gson.fromJson(this.readAgentsFromFile(), AgentImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (AgentImportDto agentImportDto : agentImportDtos) {
            boolean isValid = this.validationUtils.isValid(agentImportDto);

            if (this.agentRepository.findByFirstName(agentImportDto.getFirstName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Agent agent = this.modelMapper.map(agentImportDto, Agent.class);

                Town town = this.townRepository.findByTownName(agentImportDto.getTown()).get();
                agent.setTown(town);

                this.agentRepository.saveAndFlush(agent);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_AGENT, agent.getFirstName(), agent.getLastName()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_AGENT).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
