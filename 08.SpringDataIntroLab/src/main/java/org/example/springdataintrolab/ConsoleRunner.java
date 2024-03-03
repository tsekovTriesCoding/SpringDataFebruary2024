package org.example.springdataintrolab;

import jakarta.transaction.Transactional;
import org.example.springdataintrolab.models.Account;
import org.example.springdataintrolab.models.User;
import org.example.springdataintrolab.services.AccountService;
import org.example.springdataintrolab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Account account = new Account();
        User user = new User("Pesho", 20, account);
        this.userService.registerUser(user);

        this.accountService.transferMoney(BigDecimal.valueOf(200), 1L);
        this.accountService.withdrawMoney(BigDecimal.valueOf(100), 1L);

    }
}
