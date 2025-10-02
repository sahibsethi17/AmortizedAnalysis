// dto/account/AccountResponse.java
package com.financeApp.AmortizedAnalysis.dto.account;

import com.financeApp.AmortizedAnalysis.model.Account;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String accountName,
        String accountType,
        BigDecimal balance,
        String currency,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long userId
) {
    public static AccountResponse from(Account a) {
        return new AccountResponse(
                a.getId(),
                a.getAccountName(),
                a.getAccountType(),
                a.getBalance(),
                a.getCurrency(),
                a.getCreatedAt(),
                a.getUpdatedAt(),
                a.getUser() != null ? a.getUser().getId() : null
        );
    }
}