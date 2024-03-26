package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SellerImportDto;
import softuni.exam.models.dto.SellersWrapperDto;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_SELLER;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_SELLER;
import static softuni.exam.constants.Paths.SELLERS_PATH;

@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository,
                             ModelMapper modelMapper,
                             ValidationUtil validationUtil,
                             XmlParser xmlParser) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(SELLERS_PATH));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        SellersWrapperDto sellersWrapperDto = this.xmlParser.fromFile(SELLERS_PATH, SellersWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (SellerImportDto seller : sellersWrapperDto.getSellers()) {
            boolean isValid = this.validationUtil.isValid(seller);

            if (isValid) {
                Seller sellerToSave = this.modelMapper.map(seller, Seller.class);

                this.sellerRepository.saveAndFlush(sellerToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_SELLER, sellerToSave.getLastName(), sellerToSave.getEmail()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_SELLER).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
