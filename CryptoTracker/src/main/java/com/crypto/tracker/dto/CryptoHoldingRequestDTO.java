package com.crypto.tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CryptoHoldingRequestDTO {

    @NotBlank(message = "Symbol is mandatory")
    private String symbol;

    @NotNull(message = "Quantity help is mandatory")
    @Positive(message = "Quantity must be greater than 0")
    private Double quantityHeld;
}
