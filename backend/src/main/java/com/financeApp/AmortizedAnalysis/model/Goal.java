package com.financeApp.AmortizedAnalysis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double targetAmount;

    @Column(nullable = false)
    private Double savedAmount;

    @Column(nullable = false)
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

}

