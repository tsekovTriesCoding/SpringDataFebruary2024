package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferImportDto;
import softuni.exam.models.dto.OffersWrapperDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.OFFERS_PATH;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository,
                            AgentRepository agentRepository,
                            ApartmentRepository apartmentRepository,
                            ModelMapper modelMapper,
                            ValidationUtils validationUtils,
                            XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        OffersWrapperDto offersWrapperDto = this.xmlParser.fromFile(OFFERS_PATH, OffersWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (OfferImportDto offer : offersWrapperDto.getOffers()) {
            boolean isValid = this.validationUtils.isValid(offer);

            if (this.agentRepository.findByFirstName(offer.getAgent().getName()).isEmpty()) {
                isValid = false;
            }

            if (isValid) {
                Offer offerToSave = this.modelMapper.map(offer, Offer.class);

                Agent agent = this.agentRepository.findByFirstName(offer.getAgent().getName()).get();
                offerToSave.setAgent(agent);

                Apartment apartment = apartmentRepository.findById(offer.getApartment().getId()).get();
                offerToSave.setApartment(apartment);

                this.offerRepository.saveAndFlush(offerToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_OFFER, offerToSave.getPrice()))
                        .append(System.lineSeparator());

            } else {
                sb.append(INVALID_OFFER).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportOffers() {
        List<Offer> bestOffers = this.offerRepository.findAllByApartmentApartmentTypeOrderByApartmentAreaDescPrice(ApartmentType.three_rooms)
                .orElseThrow(NoSuchElementException::new);

        StringBuilder sb = new StringBuilder();

        for (Offer bestOffer : bestOffers) {
            sb.append(String.format(EXPORT_FORMAT, bestOffer.getAgent().getFirstName(), bestOffer.getAgent().getLastName(), bestOffer.getId(),
                            bestOffer.getApartment().getArea(),
                            bestOffer.getApartment().getTown().getTownName(),
                            bestOffer.getPrice()));
        }

        return sb.toString().trim();
    }
}
