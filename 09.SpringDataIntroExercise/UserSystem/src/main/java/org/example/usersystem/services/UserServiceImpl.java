package org.example.usersystem.services;

import org.example.usersystem.entities.User;
import org.example.usersystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsersByEmailProvider(String provider) {
        return this.userRepository.findAllByEmailEndingWith(provider);
    }

    @Override
    public long getUsersCount() {
        return this.userRepository.count();
    }

    @Override
    public void markInactiveUsersForDeletion(LocalDateTime date) {
        for (User u : this.userRepository.findAllByLastTimeLoggedInBefore(date)) {
            u.setIsDeleted(true);
            this.userRepository.saveAndFlush(u);
        }

    }

    @Override
    public long getCountOfUsersMarkedForDeletion() {
        return this.userRepository.findAll().
                stream().
                filter(User::isDeleted)
                .count();
    }

    @Override
    public void deleteAllUsersMarkedForDeletion() {
        List<User> usersForDeletion = this.userRepository.findAll().
                stream().
                filter(User::isDeleted)
                .toList();

        this.userRepository.deleteAll(usersForDeletion);

    }

}
