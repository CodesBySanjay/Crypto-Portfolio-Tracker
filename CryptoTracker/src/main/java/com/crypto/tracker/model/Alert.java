package com.crypto.tracker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alert")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String cryptoSymbol;
    private Double targetPrice;
    private String alertType;
    private boolean triggered;
}
