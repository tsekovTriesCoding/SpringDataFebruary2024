package org.example.usersystem;

import org.example.usersystem.entities.User;
import org.example.usersystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;

    @Autowired
    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.userService.getUsersCount() == 0L) {
            addUsersUsingProviderFromBulgaria();
            addUsersUsingGmailAsAProvider();
        }

//        System.out.println("Enter gmail.com or mail.bg to check the users that use the entered email provider!");
//
//        Scanner scanner = new Scanner(System.in);
//        String emailProvider = scanner.nextLine();

//        printAllUsersByEmailProvider(emailProvider);
//
        markUsersForDeletionIfInactiveSinceDate();
        System.out.println(this.userService.getCountOfUsersMarkedForDeletion());
        this.userService.deleteAllUsersMarkedForDeletion();
    }

    private void markUsersForDeletionIfInactiveSinceDate() {
        this.userService.markInactiveUsersForDeletion(LocalDateTime.now());
    }

    private void addUsersUsingGmailAsAProvider() {
        for (int i = 1; i <= 50; i++) {
            this.userService.save(User.builder.newInstance()
                    .username("gmailUSERNAME" + i)
                    .password("pass" + i + i + "word")
                    .email(i + "test@gmail.com")
                    .age(i + 5)
                    .firstName("FirstName" + i)
                    .lastName("LastName" + i)
                    .registeredOn(LocalDateTime.now())
                    .lastTimeLoggedIn(LocalDateTime.now())
                    .isDeleted(false)
                    .build());
        }
    }

    private void printAllUsersByEmailProvider(String provider) {
        this.userService.getAllUsersByEmailProvider(provider)
                .forEach(user -> System.out.println(user.getUsername() + " " + user.getEmail()));
    }

    private void addUsersUsingProviderFromBulgaria() {
        for (int i = 1; i <= 200; i++) {
            this.userService.save(User.builder.newInstance()
                    .username("username" + i)
                    .password("pass" + i + "wo" + i / 2 + "rd")
                    .email(i + "newEMAIL@mail.bg")
                    .age(i / 2 + 1)
                    .firstName("FirstName" + i)
                    .lastName("LastName" + i)
                    .registeredOn(LocalDateTime.now())
                    .lastTimeLoggedIn(LocalDateTime.now())
                    .isDeleted(false)
                    .build());
        }
    }
}
