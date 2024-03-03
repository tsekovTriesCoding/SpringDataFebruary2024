package org.example.springdataintrolab.services;

import org.example.springdataintrolab.models.User;

public interface UserService {
    void registerUser(User user);
    User findByUsername(String username);
}
