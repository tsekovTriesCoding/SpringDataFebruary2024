package org.example.springdataautomappingobjectsexercise.services.game;

import org.example.springdataautomappingobjectsexercise.domain.dtos.game.AddGame;
import org.example.springdataautomappingobjectsexercise.domain.entities.Game;
import org.example.springdataautomappingobjectsexercise.repositories.GameRepository;
import org.example.springdataautomappingobjectsexercise.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.example.springdataautomappingobjectsexercise.constants.Exceptions.GAME_DOES_NOT_EXIST;
import static org.example.springdataautomappingobjectsexercise.constants.Exceptions.USER_DOES_NOT_HAVE_ADMIN_RIGHTS;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserService userService;

    private final ModelMapper modelMapper = new ModelMapper();


    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public String addGame(String[] args) {
        if (this.userService.getLoggedInUser().getIsAdministrator()) {
            String title = args[1];
            BigDecimal price = new BigDecimal(args[2]);
            float size = Float.parseFloat(args[3]);
            String trailer = args[4];
            String thumbnailURL = args[5];
            String description = args[6];
            String[] dateInfo = args[7].split("-");
            LocalDate releaseDate = LocalDate.of(Integer.parseInt(dateInfo[2]),
                    Integer.parseInt(dateInfo[1]),
                    Integer.parseInt(dateInfo[0]));

            AddGame addGame;
            try {
                addGame = new AddGame(title, price, size, trailer, thumbnailURL, description, releaseDate);
            } catch (IllegalArgumentException e) {
                return e.getMessage();
            }

            Game game = this.modelMapper.map(addGame, Game.class);

            this.gameRepository.save(game);

            return addGame.addedGame();
        }

        return USER_DOES_NOT_HAVE_ADMIN_RIGHTS;
    }

    @Override
    public String editGame(String[] args) {
        if (this.userService.getLoggedInUser().getIsAdministrator()) {
            int gameId = Integer.parseInt(args[1]);
            Map<String, String> newValues = new HashMap<>();

            Arrays.stream(args).skip(2).forEach(s -> {
                String[] newValuesInfo = s.split("=");
                if (s.contains("title")) {
                    newValues.put(newValuesInfo[0], newValuesInfo[1]);
                } else if (s.contains("size")) {
                    newValues.put(newValuesInfo[0], newValuesInfo[1]);
                } else if (s.contains("price")) {
                    newValues.put(newValuesInfo[0], newValuesInfo[1]);
                } else if (s.contains("trailer")) {
                    newValues.put(newValuesInfo[0], newValuesInfo[1]);
                } else if (s.contains("thumbnail")) {
                    newValues.put(newValuesInfo[0], newValuesInfo[1]);
                } else {
                    newValues.put(newValuesInfo[0], newValuesInfo[1]);
                }
            });

            Optional<Game> gameToEdit = this.gameRepository.findById(gameId);

            if (gameToEdit.isPresent()) {
                Game game = gameToEdit.get();

                if (newValues.containsKey("title")) {
                    game.setTitle(newValues.get("title"));
                }

                if (newValues.containsKey("size")) {
                    game.setSize(Float.parseFloat(newValues.get("size")));
                }

                if (newValues.containsKey("price")) {
                    game.setPrice(new BigDecimal(newValues.get("price")));
                }

                if (newValues.containsKey("trailer")) {
                    game.setTrailer(newValues.get("trailer"));
                }

                if (newValues.containsKey("thumbnail")) {
                    game.setTrailer(newValues.get("thumbnail"));
                }

                if (newValues.containsKey("description")) {
                    game.setTrailer(newValues.get("description"));
                }

                this.gameRepository.save(game);
                return "Edited " + game.getTitle();
            }

            return GAME_DOES_NOT_EXIST;
        }

        return USER_DOES_NOT_HAVE_ADMIN_RIGHTS;
    }

    @Override
    public String deleteGame(long id) {
        if (this.userService.getLoggedInUser().getIsAdministrator()) {
            Optional<Game> gameToDelete = this.gameRepository.findById(id);

            if (gameToDelete.isPresent()) {
                this.gameRepository.delete(gameToDelete.get());
                return "deleted " + gameToDelete.get().getTitle();
            }

            return GAME_DOES_NOT_EXIST;
        }

        return USER_DOES_NOT_HAVE_ADMIN_RIGHTS;
    }

    @Override
    public List<Game> getAllGames() {
        return this.gameRepository.findAll();
    }

    @Override
    public Game getGameDetails(String title) {
        return this.gameRepository.findByTitle(title).orElseThrow(NoSuchElementException::new);
    }

}
