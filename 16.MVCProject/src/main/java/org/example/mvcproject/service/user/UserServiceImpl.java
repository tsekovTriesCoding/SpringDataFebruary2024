package org.example.mvcproject.service.user;

import org.example.mvcproject.models.dto.user.UserRegisterDto;
import org.example.mvcproject.models.entity.User;
import org.example.mvcproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(UserRegisterDto userRegisterDto) {
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userRegisterDto, User.class);

        boolean emailExists = userRepository.existsByEmail(userRegisterDto.getEmail());

        if (emailExists) {
            return false;
        }

        userRepository.save(user);
        return true;
    }

}

