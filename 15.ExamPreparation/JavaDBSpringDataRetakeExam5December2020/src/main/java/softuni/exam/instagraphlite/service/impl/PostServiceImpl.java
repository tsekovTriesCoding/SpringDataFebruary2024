package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PostImportDto;
import softuni.exam.instagraphlite.models.dto.PostsWrapperDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtils;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.instagraphlite.constants.Messages.INVALID_POST;
import static softuni.exam.instagraphlite.constants.Messages.SUCCESSFULLY_IMPORTED_POST;
import static softuni.exam.instagraphlite.constants.Paths.POSTS_PATH;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           PictureRepository pictureRepository,
                           UserRepository userRepository,
                           ModelMapper modelMapper,
                           ValidationUtils validationUtils,
                           XmlParser xmlParser) {
        this.postRepository = postRepository;
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POSTS_PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        PostsWrapperDto postsWrapperDto = this.xmlParser.fromFile(POSTS_PATH, PostsWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (PostImportDto post : postsWrapperDto.getPosts()) {
            boolean isValid = this.validationUtils.isValid(post);

            if (this.userRepository.findByUsername(post.getUser().getUsername()).isEmpty() ||
                    this.pictureRepository.findByPath(post.getPicture().getPath()).isEmpty()) {
                isValid = false;
            }
            if (isValid) {
                Post postToSave = this.modelMapper.map(post, Post.class);

                User user = this.userRepository.findByUsername(post.getUser().getUsername()).get();
                Picture picture = this.pictureRepository.findByPath(post.getPicture().getPath()).get();

                postToSave.setUser(user);
                postToSave.setPicture(picture);

                this.postRepository.saveAndFlush(postToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_POST, postToSave.getUser().getUsername()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_POST).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
