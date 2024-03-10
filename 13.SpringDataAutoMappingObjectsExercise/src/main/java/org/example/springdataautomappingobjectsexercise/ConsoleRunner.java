package org.example.springdataautomappingobjectsexercise;

import org.example.springdataautomappingobjectsexercise.domain.entities.Game;
import org.example.springdataautomappingobjectsexercise.services.game.GameService;
import org.example.springdataautomappingobjectsexercise.services.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.example.springdataautomappingobjectsexercise.constants.Commands.*;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final GameService gameService;

    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        String input = scanner.nextLine();


        while (!input.equals("END")) {
            String[] info = input.split("\\|");
            String command = info[0];
            String output;

            switch (command) {
                case REGISTER_USER:
                    output = this.userService.registerUser(info);
                    break;
                case LOGIN_USER:
                    output = this.userService.loginUser(info);
                    break;
                case LOGOUT_USER:
                    output = this.userService.logout(info);
                    break;
                case ADD_GAME:
                    output = this.gameService.addGame(info);
                    break;
                case EDIT_GAME:
                    output = this.gameService.editGame(info);
                    break;
                case DELETE_GAME:
                    long id = Long.parseLong(info[1]);
                    output = this.gameService.deleteGame(id);
                    break;
                case ALL_GAMES:
                    List<Game> allGames = this.gameService.getAllGames();
                    output = allGames
                            .stream()
                            .map(Game::gameTitleAndPriceFormat)
                            .collect(Collectors.joining(System.lineSeparator()));
                    break;
                case DETAIL_GAME:
                    Game game = this.gameService.getGameDetails(info[1]);
                    output = game.gameDetailsFormat();
                    break;
                case OWNED_GAMES:
                    output = this.userService.getLoggedInUserGames();
                    break;
                case ADD_ITEM:
                    output = this.userService.addGameToShoppingCart(info[1]);
                    break;
                case REMOVE_ITEM:
                    output = this.userService.removeItemFromShoppingCart(info[1]);
                    break;
                case BUY_ITEM:
                    output = this.userService.buyGames();
                    break;
                default:
                    output = "Invalid command!";
            }

            System.out.println(output);

            input = scanner.nextLine();
        }

    }
}
