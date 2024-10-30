package com.financeApp.AmortizedAnalysis.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Goal {


    private String name;
    private Double targetAmount;
    private Double savedAmount;

}

