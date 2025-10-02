package com.financeApp.AmortizedAnalysis.controller;

import com.financeApp.AmortizedAnalysis.dto.account.AccountResponse;
import com.financeApp.AmortizedAnalysis.dto.account.CreateAccountRequest;
import com.financeApp.AmortizedAnalysis.model.Account;
import com.financeApp.AmortizedAnalysis.model.Transaction;
import com.financeApp.AmortizedAnalysis.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Create an account
    @PostMapping("/create/{userId}")
    public ResponseEntity<AccountResponse> createAccount(
            @PathVariable Long userId,
            @RequestBody CreateAccountRequest req) {
        try {
            Account created = accountService.createAccountForUser(userId, req);
            return ResponseEntity.ok(AccountResponse.from(created));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

//    // Get all accounts (or optionally get by userId if you want user-specific)
//    // Example: /api/accounts?userId=123
//    @GetMapping
//    public ResponseEntity<List<Account>> getAccounts(@RequestParam(value = "userId", required = false) Long userId) {
//        try {
//            List<Account> accounts;
//            if (userId != null) {
//                accounts = accountService.getAccountsByUserId(userId);
//            } else {
//                accounts = accountService.getAllAccounts();
//            }
//            return ResponseEntity.ok(accounts);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }

    // list *my* accounts
    @GetMapping
    public ResponseEntity<List<AccountResponse>> listMyAccounts() {
        String me = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Account> accounts = accountService.getAccountsByUsername(me);
        List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            accountResponses.add(AccountResponse.from(account));
        }
        return ResponseEntity.ok(accountResponses);
    }

    // admin: list by userId
    @GetMapping(params = "userId") // ?userId=__
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountResponse>> listByUser(@RequestParam Long userId) {
        var accounts = accountService.getAccountsByUserId(userId);
        List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            accountResponses.add(AccountResponse.from(account));
        }
        return ResponseEntity.ok(accountResponses);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<AccountResponse> getOne(@PathVariable Long id) {
//        var acc = accountService.getAccountOwnedByCaller(id); // throws if not owner
//        return ResponseEntity.ok(AccountResponse.from((Account) acc));
//    }

    // Get a transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        try {
            Account account = accountService.getAccountById(id);
            return ResponseEntity.ok(account);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Update an account
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
        try {
            Account updatedAccount = accountService.updateAccount(id, accountDetails);
            return ResponseEntity.ok(updatedAccount);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete an account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        try {
            String result = accountService.deleteAccount(id);
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}