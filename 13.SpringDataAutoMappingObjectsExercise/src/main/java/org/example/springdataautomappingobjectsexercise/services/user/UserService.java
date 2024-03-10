package org.example.springdataautomappingobjectsexercise.services.user;


import org.example.springdataautomappingobjectsexercise.domain.entities.Game;
import org.example.springdataautomappingobjectsexercise.domain.entities.User;

import java.util.List;

public interface UserService {
    String registerUser(String[] args);

    String loginUser(String[] args);

    String logout(String[] args);

    User getLoggedInUser();

    void addGamesToLoggedInUser(List<Game> games);

    String getLoggedInUserGames();

    String addGameToShoppingCart(String title);

    String removeItemFromShoppingCart(String title);

    String buyGames();
}
