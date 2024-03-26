package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PictureImportDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Picture;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_PICTURE;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_PICTURE;
import static softuni.exam.constants.Paths.PICTURES_PATH;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final CarRepository carRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository,
                              CarRepository carRepository,
                              Gson gson,
                              ValidationUtil validationUtil,
                              ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURES_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        PictureImportDto[] pictureImportDtos = this.gson.fromJson(this.readPicturesFromFile(), PictureImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (PictureImportDto pictureImportDto : pictureImportDtos) {
            boolean isValid = this.validationUtil.isValid(pictureImportDto);

            if (isValid) {
                Picture picture = this.modelMapper.map(pictureImportDto, Picture.class);

                if (this.carRepository.findById(pictureImportDto.getCar()).isPresent()) {
                    Car car = this.carRepository.findById(pictureImportDto.getCar()).get();

                    picture.setCar(car);
                }

                this.pictureRepository.saveAndFlush(picture);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_PICTURE, picture.getName()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_PICTURE).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
