package com.financeApp.AmortizedAnalysis.service;

import com.financeApp.AmortizedAnalysis.model.Account;
import com.financeApp.AmortizedAnalysis.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountsByUserId(Long userId) {
        // Assuming Account has a userId foreign key field or relationship
        return accountRepository.findByUserId(userId);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account existingAccount = getAccountById(id);
        existingAccount.setAccountName(accountDetails.getAccountName());
        existingAccount.setBalance(accountDetails.getBalance());
        existingAccount.setCurrency(accountDetails.getCurrency());
        existingAccount.setAccountType(accountDetails.getAccountType());
        existingAccount.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(existingAccount);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}