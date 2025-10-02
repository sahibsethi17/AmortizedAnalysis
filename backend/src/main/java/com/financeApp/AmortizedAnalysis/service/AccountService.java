package com.financeApp.AmortizedAnalysis.service;

import com.financeApp.AmortizedAnalysis.dto.account.CreateAccountRequest;
import com.financeApp.AmortizedAnalysis.model.Account;
import com.financeApp.AmortizedAnalysis.model.Users;
import com.financeApp.AmortizedAnalysis.repo.AccountRepo;
import com.financeApp.AmortizedAnalysis.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Autowired
    private UserRepo userRepo;

    public Account createAccountForUser(Long userId, CreateAccountRequest req) {
        // auth user
        String currentUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();

        Users caller = Optional.ofNullable(userRepo.findByUsername(currentUsername))
                .orElseThrow(() -> new IllegalStateException("Caller not found"));

        // block creating accounts for someone else (unless ADMIN)
        if (!caller.getId().equals(userId) && !hasRole("ADMIN")) {
            throw new AccessDeniedException("You can only create accounts for your own user.");
        }

        Users target = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account acc = new Account();
        acc.setAccountName(req.accountName());
        acc.setAccountType(req.accountType());
        acc.setBalance(req.balance());
        acc.setCurrency(req.currency());
        acc.setUser(target); // <— attach managed user (NOT NULL satisfied)

        return accountRepository.save(acc);
    }

    private boolean hasRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
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
        if (accountDetails.getAccountName() != null) existingAccount.setAccountName(accountDetails.getAccountName());
        if (accountDetails.getBalance() != null) existingAccount.setBalance(accountDetails.getBalance());
        if (accountDetails.getCurrency() != null) existingAccount.setCurrency(accountDetails.getCurrency());
        if (accountDetails.getAccountType() != null) existingAccount.setAccountType(accountDetails.getAccountType());
        existingAccount.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(existingAccount);
    }

    @Transactional
    public String deleteAccount(Long accountId) {
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        boolean isOwner = acc.getUser() != null &&
                SecurityContextHolder.getContext().getAuthentication().getName()
                        .equals(acc.getUser().getUsername());
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isOwner && !isAdmin) throw new AccessDeniedException("You can only delete your own account.");

        accountRepository.delete(acc);
        return "Deleted account successfully.";
    }

    public List<Account> getAccountsByUsername(String username) {
        return accountRepository.findByUserUsername(username);
    }

    public Object getAccountOwnedByCaller(Long id) {
        return accountRepository.findById(id);
    }
}