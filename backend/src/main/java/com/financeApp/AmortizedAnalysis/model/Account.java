package com.financeApp.AmortizedAnalysis.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private String accountType; // e.g., "Checking", "Savings", "Credit Card", "Investment"

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String currency; // e.g., "USD", "CAD"

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany
    private List<Transaction> transactions;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;
}
