package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PersonImportDto;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import static softuni.exam.constants.Messages.INVALID_PERSON;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_PERSON;
import static softuni.exam.constants.Paths.PEOPLE_PATH;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository,
                             Gson gson,
                             ValidationUtil validationUtil,
                             ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(Path.of(PEOPLE_PATH));
    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        PersonImportDto[] personImportDtos = this.gson.fromJson(this.readPeopleFromFile(), PersonImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (PersonImportDto personImportDto : personImportDtos) {
            boolean isValid = this.validationUtil.isValid(personImportDto);

            if (this.personRepository.findByFirstNameOrEmailOrPhone(personImportDto.getFirstName(),
                    personImportDto.getEmail(),
                    personImportDto.getPhone()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Person person = this.modelMapper.map(personImportDto, Person.class);

                this.personRepository.saveAndFlush(person);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_PERSON, person.getFirstName(), person.getLastName()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_PERSON).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
