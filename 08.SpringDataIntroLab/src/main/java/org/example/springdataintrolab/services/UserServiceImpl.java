package org.example.springdataintrolab.services;

import org.example.springdataintrolab.models.User;
import org.example.springdataintrolab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        Optional<User> byUsername = this.userRepository.getByUsername(user.getUsername());

        if (byUsername.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        this.userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.getByUsername(username).get();
    }
}
