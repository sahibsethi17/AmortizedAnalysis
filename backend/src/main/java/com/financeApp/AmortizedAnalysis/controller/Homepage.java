package com.financeApp.AmortizedAnalysis.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Homepage {

    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        return "This is the homepage! Running on Session ID: " + request.getSession().getId();
    }
}
