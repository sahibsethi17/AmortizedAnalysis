package com.financeApp.AmortizedAnalysis.service;

import com.financeApp.AmortizedAnalysis.model.Account;
import com.financeApp.AmortizedAnalysis.model.Transaction;
import com.financeApp.AmortizedAnalysis.model.Users;
import com.financeApp.AmortizedAnalysis.repo.AccountRepo;
import com.financeApp.AmortizedAnalysis.repo.TransactionRepo;
import com.financeApp.AmortizedAnalysis.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private UserRepo userRepo;

    public Transaction createTransaction(Long userId, Long accountId, Transaction transaction) {
        // auth user
        String currentUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();

        Users caller = Optional.ofNullable(userRepo.findByUsername(currentUsername))
                .orElseThrow(() -> new IllegalStateException("Caller not found"));

        // block creating transactions for someone else (unless ADMIN)
        if (!caller.getId().equals(userId) && !hasRole("ADMIN")) {
            throw new AccessDeniedException("You can only create accounts for your own user.");
        }

//        Users target = userRepo.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account acc = accountRepo.findByIdAndUserUsername(accountId, currentUsername);

        if (acc == null) {
            throw new IllegalArgumentException("Account not found.");
        }

        Transaction tx = new Transaction();
        tx.setAmount(transaction.getAmount());
        tx.setCategory(transaction.getCategory());
        tx.setTransactionDate(transaction.getTransactionDate());
        tx.setUser(caller);
        tx.setAccount(acc);

        return transactionRepository.save(tx);


//        Account acc = new Account();
//        acc.setAccountName(req.accountName());
//        acc.setAccountType(req.accountType());
//        acc.setBalance(req.balance());
//        acc.setCurrency(req.currency());
//        acc.setUser(target); // <— attach managed user (NOT NULL satisfied)
//
//        return accountRepository.save(acc);
    }

    private boolean hasRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction existingTransaction = getTransactionById(id);
        // Update fields
        existingTransaction.setAmount(transactionDetails.getAmount());
        existingTransaction.setCategory(transactionDetails.getCategory());
        existingTransaction.setAmount(transactionDetails.getAmount());
        existingTransaction.setTransactionDate(transactionDetails.getTransactionDate());
        return transactionRepository.save(existingTransaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}