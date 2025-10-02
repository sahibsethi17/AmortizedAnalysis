package com.financeApp.AmortizedAnalysis.dto.account;

import java.math.BigDecimal;

public record CreateAccountRequest(
        String accountName,
        String accountType,
        BigDecimal balance,
        String currency
) {}