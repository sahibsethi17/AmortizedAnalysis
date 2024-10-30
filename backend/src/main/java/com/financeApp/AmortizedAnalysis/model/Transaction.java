package com.financeApp.AmortizedAnalysis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.Date;


@Setter
@Getter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Users user;

}
