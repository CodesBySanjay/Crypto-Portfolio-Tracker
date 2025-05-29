package com.crypto.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoHoldingResponseDTO {
    private Long id;
    private int serialNumber;
    private String coinName;
    private String symbol;
    private double quantityHeld;
    private double buyPrice;
    private Double currentPrice;
    private double profitLoss;
    private String buyDate;
}
