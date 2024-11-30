package com.financeApp.AmortizedAnalysis.service;

import com.financeApp.AmortizedAnalysis.model.Account;
import com.financeApp.AmortizedAnalysis.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountService {

    @Autowired
    private AccountRepo repo;

    public Account createAccount(Account account) {
        return repo.save(account);
    }
}
