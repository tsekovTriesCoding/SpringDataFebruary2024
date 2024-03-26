package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.UserImportDto;
import softuni.exam.instagraphlite.models.dto.UserWithPostExportDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.constants.Messages.INVALID_USER;
import static softuni.exam.instagraphlite.constants.Messages.SUCCESSFULLY_IMPORTED_USER;
import static softuni.exam.instagraphlite.constants.Paths.USERS_PATH;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PictureRepository pictureRepository,
                           Gson gson,
                           ValidationUtils validationUtils,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USERS_PATH));
    }

    @Override
    public String importUsers() throws IOException {
        UserImportDto[] userImportDtos = this.gson.fromJson(this.readFromFileContent(), UserImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (UserImportDto userImportDto : userImportDtos) {
            boolean isValid = this.validationUtils.isValid(userImportDto);

            if (this.userRepository.findByUsername(userImportDto.getUsername()).isPresent() ||
                    this.pictureRepository.findByPath(userImportDto.getProfilePicture()).isEmpty()) {
                isValid = false;
            }

            if (isValid) {
                User user = this.modelMapper.map(userImportDto, User.class);
                Picture picture = this.pictureRepository.findByPath(userImportDto.getProfilePicture()).get();

                user.setPicture(picture);

                this.userRepository.saveAndFlush(user);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_USER, user.getUsername()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_USER).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        String exportInfo = this.userRepository.findAllUsersWithPosts().orElseThrow(NoSuchElementException::new)
                .stream()
                .map(UserWithPostExportDto::new)
                .map(UserWithPostExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));


        return exportInfo.trim();
    }
}
