package com.crypto.tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_holding")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoHolding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String coinName;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private Double quantityHeld;

    @Column(nullable = false)
    private Double buyPrice;

    private Double currentPrice;

    @Column(nullable = false)
    private LocalDateTime buyDate;

    private Double profitLoss;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
