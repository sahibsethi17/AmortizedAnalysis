package com.financeApp.AmortizedAnalysis.repo;

import com.financeApp.AmortizedAnalysis.model.Account;
import com.financeApp.AmortizedAnalysis.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long userId);

    List<Account> findByUserUsername(String username);
    void deleteByIdAndUserUsername(Long id, String username);

    Account findByIdAndUser_Id(Long id, Long userId);

    // or by username
    Account findByIdAndUserUsername(Long id, String username);

}
