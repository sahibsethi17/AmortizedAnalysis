package com.financeApp.AmortizedAnalysis.controller;

import com.financeApp.AmortizedAnalysis.model.Transaction;
import com.financeApp.AmortizedAnalysis.model.Users;
import com.financeApp.AmortizedAnalysis.service.TransactionService;
import com.financeApp.AmortizedAnalysis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService usersService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Transaction> createTransaction(@PathVariable int userId, @RequestBody Transaction transaction) {
        Optional<Users> user = usersService.getUserById(userId);
        if (user.isPresent()) {
            transaction.setUser(user.get());
            return ResponseEntity.ok(transactionService.createTransaction(transaction));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable int userId) {
        Optional<Users> user = usersService.getUserById(userId);
        return user.map(value -> ResponseEntity.ok(transactionService.getTransactionsByUser(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
