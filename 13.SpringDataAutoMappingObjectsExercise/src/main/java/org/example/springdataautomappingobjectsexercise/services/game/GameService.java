package org.example.springdataautomappingobjectsexercise.services.game;

import org.example.springdataautomappingobjectsexercise.domain.entities.Game;

import java.util.List;

public interface GameService {
    String addGame(String[] args);

    String editGame(String[] args);

    String deleteGame(long id);

    List<Game> getAllGames();

    Game getGameDetails(String title);

}
