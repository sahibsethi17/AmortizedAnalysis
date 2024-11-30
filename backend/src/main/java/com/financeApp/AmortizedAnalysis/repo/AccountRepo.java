package com.financeApp.AmortizedAnalysis.repo;

import com.financeApp.AmortizedAnalysis.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long id);
}
