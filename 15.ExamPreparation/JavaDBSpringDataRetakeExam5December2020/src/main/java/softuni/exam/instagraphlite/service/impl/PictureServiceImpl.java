package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PictureExportDto;
import softuni.exam.instagraphlite.models.dto.PictureImportDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.constants.Messages.INVALID_PICTURE;
import static softuni.exam.instagraphlite.constants.Messages.SUCCESSFULLY_IMPORTED_PICTURE;
import static softuni.exam.instagraphlite.constants.Paths.PICTURES_PATH;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository,
                              Gson gson,
                              ValidationUtils validationUtils,
                              ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURES_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        PictureImportDto[] pictureImportDtos = this.gson.fromJson(this.readFromFileContent(), PictureImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (PictureImportDto pictureImportDto : pictureImportDtos) {
            boolean isValid = this.validationUtils.isValid(pictureImportDto);

            if (this.pictureRepository.findByPath(pictureImportDto.getPath()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Picture picture = this.modelMapper.map(pictureImportDto, Picture.class);
                this.pictureRepository.saveAndFlush(picture);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_PICTURE, picture.getSize()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_PICTURE).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportPictures() {
        String exportInfo = this.pictureRepository.findAllBySizeGreaterThanOrderBySize(30000.0)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(p -> this.modelMapper.map(p, PictureExportDto.class))
                .map(PictureExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));


        return exportInfo.trim();
    }
}
