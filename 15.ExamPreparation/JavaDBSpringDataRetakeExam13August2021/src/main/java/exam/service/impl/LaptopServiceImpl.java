package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.BestLaptopDto;
import exam.model.dto.LaptopImportDto;
import exam.model.entity.Laptop;
import exam.model.entity.Shop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static exam.constants.Messages.INVALID_LAPTOP;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_LAPTOPS;
import static exam.constants.Paths.LAPTOPS_PATH;

@Service
public class LaptopServiceImpl implements LaptopService {
    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository,
                             ShopRepository shopRepository,
                             Gson gson,
                             ValidationUtils validationUtils,
                             ModelMapper modelMapper) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOPS_PATH));
    }

    @Override
    public String importLaptops() throws IOException {
        LaptopImportDto[] laptopImportDtos = this.gson.fromJson(this.readLaptopsFileContent(), LaptopImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (LaptopImportDto laptopImportDto : laptopImportDtos) {
            boolean isValid = this.validationUtils.isValid(laptopImportDto);

            if (this.laptopRepository.findByMacAddress(laptopImportDto.getMacAddress()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Laptop laptopToSave = this.modelMapper.map(laptopImportDto, Laptop.class);

                Shop shop = this.shopRepository.findByName(laptopImportDto.getShop().getName()).get();
                laptopToSave.setShop(shop);

                this.laptopRepository.saveAndFlush(laptopToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_LAPTOPS, laptopToSave.getMacAddress(),
                                laptopToSave.getCpuSpeed(), laptopToSave.getRam(), laptopToSave.getStorage()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_LAPTOP).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportBestLaptops() {
        String exportInfo = this.laptopRepository.findBestLaptops()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(BestLaptopDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return exportInfo.trim();
    }
}
