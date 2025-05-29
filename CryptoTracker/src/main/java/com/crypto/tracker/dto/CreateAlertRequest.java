package com.crypto.tracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAlertRequest {
    private String cryptoSymbol;
    private Double targetPrice;
    private String alertType;
}
