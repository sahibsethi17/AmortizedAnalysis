package com.financeApp.AmortizedAnalysis.controller;

import com.financeApp.AmortizedAnalysis.model.Transaction;
import com.financeApp.AmortizedAnalysis.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Create a transaction
    @PostMapping("/create/{userId}/{accountId}")
    public ResponseEntity<Transaction> createTransaction(@PathVariable Long userId,
                                                             @PathVariable Long accountId,
                                                             @RequestBody Transaction transaction) {
        try {
            Transaction createdTransaction = transactionService.createTransaction(userId, accountId, transaction);
            return ResponseEntity.ok(createdTransaction);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all transactions (optionally by accountId) /transactions?{accountId}
    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam(value = "accountId", required = false) Long accountId) {
        try {
            List<Transaction> transactions;
            if (accountId != null) {
                transactions = transactionService.getTransactionsByAccountId(accountId);
            } else {
                transactions = transactionService.getAllTransactions();
            }
            return ResponseEntity.ok(transactions);
        }  catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get a transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        try {
            Transaction transaction = transactionService.getTransactionById(id);
            return ResponseEntity.ok(transaction);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Update a transaction
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionDetails) {
        try {
            Transaction updatedTransaction = transactionService.updateTransaction(id, transactionDetails);
            return ResponseEntity.ok(updatedTransaction);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.ok("Transaction deleted successfully");
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}