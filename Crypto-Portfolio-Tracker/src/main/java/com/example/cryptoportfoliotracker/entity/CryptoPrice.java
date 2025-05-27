package com.example.cryptoportfoliotracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CryptoPrice {
    @Id
    private String symbol;
    private double price;
    private LocalDateTime timestamp;
}
