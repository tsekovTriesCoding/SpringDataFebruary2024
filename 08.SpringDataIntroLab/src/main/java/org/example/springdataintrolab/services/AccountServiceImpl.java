package org.example.springdataintrolab.services;

import org.example.springdataintrolab.models.Account;
import org.example.springdataintrolab.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long id) {
        Optional<Account> accountById = accountRepository.findAccountById(id);

        if (accountById.isEmpty()) {
            throw new RuntimeException("Account does not exist");
        }

//        if (accountById.get().getUser() == null) {
//            throw new RuntimeException("Account does not belong to any user");
//        }

        BigDecimal currentBalance = accountById.get().getBalance();

        if (amount.compareTo(currentBalance) > 0) {
            throw new RuntimeException("Insufficient money");
        }

        accountById.get().setBalance(currentBalance.subtract(amount));

        this.accountRepository.save(accountById.get());
    }

    @Override
    public void transferMoney(BigDecimal amount, Long id) {
        Account account = this.accountRepository.findAccountById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

//        if (account.getUser() == null) {
//            throw new RuntimeException("Account does not belong to any user");
//        }

        if (amount.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new RuntimeException("Transfer amount has to be more than 0");
        }

        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);

        this.accountRepository.save(account);
    }
}
