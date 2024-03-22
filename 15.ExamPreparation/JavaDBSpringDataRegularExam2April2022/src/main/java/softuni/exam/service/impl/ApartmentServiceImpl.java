package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDto;
import softuni.exam.models.dto.ApartmentsWrapperDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_APARTMENT;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_APARTMENT;
import static softuni.exam.constants.Paths.APARTMENTS_PATH;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository,
                                TownRepository townRepository,
                                ModelMapper modelMapper,
                                ValidationUtils validationUtils,
                                XmlParser xmlParser) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENTS_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        ApartmentsWrapperDto apartmentsWrapperDto = this.xmlParser.fromFile(APARTMENTS_PATH, ApartmentsWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (ApartmentImportDto apartment : apartmentsWrapperDto.getApartments()) {
            boolean isValid = this.validationUtils.isValid(apartment);

            if (this.apartmentRepository.findByTownTownNameAndArea(apartment.getTown(), apartment.getArea()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Apartment apartmentToSave = this.modelMapper.map(apartment, Apartment.class);
                Town town = this.townRepository.findByTownName(apartment.getTown()).get();

                apartmentToSave.setTown(town);

                this.apartmentRepository.saveAndFlush(apartmentToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_APARTMENT, apartmentToSave.getApartmentType(), apartmentToSave.getArea()))
                        .append(System.lineSeparator());

            } else {
                sb.append(INVALID_APARTMENT).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
