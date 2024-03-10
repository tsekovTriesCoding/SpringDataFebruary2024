package org.example.springdataautomappingobjectsexercise.services.user;

import org.example.springdataautomappingobjectsexercise.domain.dtos.user.LoginUser;
import org.example.springdataautomappingobjectsexercise.domain.dtos.user.RegisterUser;
import org.example.springdataautomappingobjectsexercise.domain.entities.Game;
import org.example.springdataautomappingobjectsexercise.domain.entities.User;
import org.example.springdataautomappingobjectsexercise.repositories.GameRepository;
import org.example.springdataautomappingobjectsexercise.repositories.UserRepository;
import org.example.springdataautomappingobjectsexercise.services.game.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.springdataautomappingobjectsexercise.constants.Exceptions.INCORRECT_USERNAME_PASSWORD_MESSAGE;
import static org.example.springdataautomappingobjectsexercise.constants.Exceptions.NO_USER_LOGGED_IN;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final GameRepository gameRepository;
    private User loggedInUser;
    private Set<Game> shoppingCart = new HashSet<>();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }


    @Override
    public String registerUser(String[] args) {
        final String email = args[1];
        final String password = args[2];
        final String confirmPassword = args[3];
        final String fullName = args[4];

        RegisterUser registerUser;

        try {
            registerUser = new RegisterUser(email, password, confirmPassword, fullName);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        final User user = this.modelMapper.map(registerUser, User.class);

        if (this.userRepository.count() == 0) {
            user.setIsAdministrator(true);
        }

        this.userRepository.save(user);

        return registerUser.successfulRegistration();
    }

    @Override
    public String loginUser(String[] args) {
        final String email = args[1];
        final String password = args[2];

        final LoginUser loginUser = new LoginUser(email, password);

        Optional<User> user = this.userRepository.findByEmail(loginUser.getEmail());

        if (user.isPresent() && this.loggedInUser == null && user.get().getPassword().equals(loginUser.getPassword())) {
            this.loggedInUser = user.get();
            return "Successfully logged in " + this.loggedInUser.getFullName();
        }

        return INCORRECT_USERNAME_PASSWORD_MESSAGE;
    }

    @Override
    public String logout(String[] args) {
        if (this.loggedInUser == null) {
            return NO_USER_LOGGED_IN;
        }
        String output = "User " + this.loggedInUser.getFullName() + " successfully logged out";
        this.loggedInUser = null;

        return output;
    }

    @Override
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    @Override
    public void addGamesToLoggedInUser(List<Game> games) {
        if (this.loggedInUser != null) {
            this.loggedInUser.addGames(games);
            this.userRepository.save(loggedInUser);
        }
    }

    @Override
    public String getLoggedInUserGames() {
        if (this.loggedInUser != null) {
            String output = "Current user has no games";
            if (!this.loggedInUser.getGames().isEmpty()) {
                output = this.loggedInUser.getGames()
                        .stream()
                        .map(Game::getTitle)
                        .collect(Collectors.joining(System.lineSeparator()));
            }

            return output;
        }

        return "Can't show owned games. No user was logged in.";
    }

    @Override
    public String addGameToShoppingCart(String title) {
        Optional<Game> byTitle = this.gameRepository.findByTitle(title);
        List<String> userGames = this.loggedInUser.getGames()
                .stream().map(Game::getTitle)
                .toList();

        List<String> gamesInCart = this.shoppingCart.stream()
                .map(Game::getTitle)
                .toList();

        if (byTitle.isPresent()) {
            if (!gamesInCart.contains(byTitle.get().getTitle()) && !userGames.contains(byTitle.get().getTitle())) {
                this.shoppingCart.add(byTitle.get());
                return title + " added to cart.";

            } else {
                return "Game already owned!";
            }

        }
        return "Game is not in the catalog";
    }

    @Override
    public String removeItemFromShoppingCart(String title) {
        List<String> gamesInCart = this.shoppingCart.stream()
                .map(Game::getTitle)
                .toList();


        if (gamesInCart.contains(title)) {
            Game game = this.gameRepository.findByTitle(title).get();
            this.shoppingCart = this.shoppingCart
                    .stream()
                    .filter(g -> !g.getTitle().equals(title))
                    .collect(Collectors.toSet());

            return title + " removed from cart.";
        }

        return "Can't remove! Game is not added to cart";
    }

    @Override
    public String buyGames() {
        if (!this.shoppingCart.isEmpty()) {
            String games = this.shoppingCart
                    .stream()
                    .map(Game::getTitle)
                    .map(s -> " -" + s)
                    .collect(Collectors.joining(System.lineSeparator()));

            this.addGamesToLoggedInUser(this.shoppingCart.stream().toList());
            return String.format("Successfully bought games:%n%s", games);
        }

        return "Shopping cart is empty!";
    }

}
