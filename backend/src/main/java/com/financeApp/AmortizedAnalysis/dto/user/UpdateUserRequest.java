package com.financeApp.AmortizedAnalysis.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateUserRequest(
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        String currency,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth,
        String gender,
        Boolean emailPreference,
        Double monthlyBudget,
        Double yearlyBudget
) {}