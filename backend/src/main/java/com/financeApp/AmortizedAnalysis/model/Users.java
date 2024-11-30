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

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "phonenumber", nullable = false)
    private String phoneNumber;

    @Column(name = "currency", nullable = false)
    private String currency = "CAD";

    @Column(name = "dateofbirth", nullable = false)
    private java.util.Date dateOfBirth;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "role", nullable = false)
    private String role = "ROLE_USER";

    @Column(name = "emailpreference", nullable = false)
    private boolean emailPreference = true;

    @Column(name = "creationdate", nullable = false)
    private java.util.Date creationDate = new java.util.Date();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    @Column(name = "monthlybudget")
    private double monthlyBudget;

    @Column(name = "yearlybudget")
    private double yearlyBudget;


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
