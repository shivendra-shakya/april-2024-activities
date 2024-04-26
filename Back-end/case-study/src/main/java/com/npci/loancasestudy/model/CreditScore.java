package com.npci.loancasestudy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "CreditScore")
@Data
public class CreditScore {
    @Id
    private String pan;
    
    private int score;
    
}