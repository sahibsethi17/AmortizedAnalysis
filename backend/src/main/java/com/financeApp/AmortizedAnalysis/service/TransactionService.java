package com.financeApp.AmortizedAnalysis.service;

import com.financeApp.AmortizedAnalysis.model.Transaction;
import com.financeApp.AmortizedAnalysis.model.Users;
import com.financeApp.AmortizedAnalysis.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo repo;

    public Transaction createTransaction(Transaction transaction) {
        return repo.save(transaction);
    }

    public List<Transaction> getTransactionsByUser(Users user) {
        return repo.findByUser(user);
    }

    public Optional<Transaction> getTransactionById(UUID id) {
        return repo.findById(id);
    }

    public void deleteTransaction(UUID id) {
        repo.deleteById(id);
    }
}
