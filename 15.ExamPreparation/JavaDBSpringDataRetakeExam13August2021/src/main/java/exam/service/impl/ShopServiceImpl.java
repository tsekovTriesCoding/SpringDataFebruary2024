package exam.service.impl;

import exam.model.dto.ShopImportDto;
import exam.model.dto.ShopsWrapperDto;
import exam.model.entity.Shop;
import exam.model.entity.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static exam.constants.Messages.INVALID_SHOP;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_SHOP;
import static exam.constants.Paths.SHOPS_PATH;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository,
                           TownRepository townRepository,
                           ModelMapper modelMapper,
                           ValidationUtils validationUtils,
                           XmlParser xmlParser) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOPS_PATH));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        ShopsWrapperDto shopsWrapperDto = this.xmlParser.fromFile(SHOPS_PATH, ShopsWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (ShopImportDto shop : shopsWrapperDto.getShops()) {
            boolean isValid = this.validationUtils.isValid(shop);

            if (this.shopRepository.findByName(shop.getName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Shop shopToSave = this.modelMapper.map(shop, Shop.class);

                if (this.townRepository.findByName(shop.getTown().getName()).isPresent()) {
                    Town town = this.townRepository.findByName(shop.getTown().getName()).get();
                    shopToSave.setTown(town);
                }

                this.shopRepository.saveAndFlush(shopToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_SHOP, shopToSave.getName(), shopToSave.getIncome()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_SHOP).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
