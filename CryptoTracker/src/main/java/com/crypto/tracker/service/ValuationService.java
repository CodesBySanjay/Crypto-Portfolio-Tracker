package com.crypto.tracker.service;

import org.springframework.stereotype.Service;

@Service
public class ValuationService {
    public double calculateCurrentValue(double quantityHeld, double currentPrice) {
        return quantityHeld * currentPrice;
    }

    public double calculatePnL(double quantityHeld, double buyPrice, double currentPrice) {
        return (currentPrice - buyPrice) * quantityHeld;
    }
}
