package org.example.usersystem.services;

import org.example.usersystem.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    void save(User user);

    List<User> getAllUsersByEmailProvider(String provider);

    long getUsersCount();

    void markInactiveUsersForDeletion(LocalDateTime date);

    long getCountOfUsersMarkedForDeletion();

    void deleteAllUsersMarkedForDeletion();

}
