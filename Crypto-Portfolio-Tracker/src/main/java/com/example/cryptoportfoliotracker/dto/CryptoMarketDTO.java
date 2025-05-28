package com.example.crypto_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CryptoMarketDTO {

    private String name;
    @NotBlank(message = "Symbol is Mandatory")
    private String symbol;
    @JsonProperty("current_price")
    private Double currentPrice;
}
