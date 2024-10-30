package com.financeApp.AmortizedAnalysis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String currency = "CAD";

    @Column(nullable = false)
    private String dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String role = "ROLE_USER";

    @Column(nullable = false)
    private boolean emailPreference = true;

    @Column(nullable = false)
    private java.util.Date creationDate = new java.util.Date();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @Column(nullable = false)
    private double monthlyBudget;

    @Column(nullable = false)
    private Goal goal;


    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
