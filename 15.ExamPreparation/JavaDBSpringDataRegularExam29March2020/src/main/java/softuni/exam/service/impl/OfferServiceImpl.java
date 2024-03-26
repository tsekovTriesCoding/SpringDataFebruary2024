package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferImportDto;
import softuni.exam.models.dto.OffersWrapperDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.Picture;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static softuni.exam.constants.Messages.INVALID_OFFER;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_OFFER;
import static softuni.exam.constants.Paths.OFFERS_PATH;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository,
                            CarRepository carRepository,
                            SellerRepository sellerRepository,
                            ModelMapper modelMapper,
                            ValidationUtil validationUtil,
                            XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
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
            boolean isValid = this.validationUtil.isValid(offer);

            if (isValid) {
                Offer offerToSave = this.modelMapper.map(offer, Offer.class);

                Car car = this.carRepository.findById(offer.getCar().getId()).get();
                offerToSave.setCar(car);

                Set<Picture> pictures = car.getPictures();
                offerToSave.setPictures(pictures);

                Seller seller = this.sellerRepository.findById(offer.getSeller().getId()).get();
                offerToSave.setSeller(seller);

                this.offerRepository.saveAndFlush(offerToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_OFFER, offerToSave.getAddedOn(), offerToSave.isHasGoldStatus()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_OFFER).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
