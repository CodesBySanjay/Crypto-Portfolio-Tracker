package com.crypto.tracker.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValuationServiceTest {

    private final ValuationService valuationService = new ValuationService();

    @Test
    void testCalculateCurrentValue() {
        double quantity = 5.0;
        double price = 200.0;

        double result = valuationService.calculateCurrentValue(quantity, price);

        assertEquals(1000.0, result, 0.00001);
    }

    @Test
    void testCalculatePnL_positive() {
        double quantity = 2.0;
        double buyPrice = 100.0;
        double currentPrice = 150.0;

        double result = valuationService.calculatePnL(quantity, buyPrice, currentPrice);

        assertEquals(100.0, result, 0.00001);
    }

    @Test
    void testCalculatePnL_negative() {
        double quantity = 3.0;
        double buyPrice = 200.0;
        double currentPrice = 150.0;

        double result = valuationService.calculatePnL(quantity, buyPrice, currentPrice);

        assertEquals(-150.0, result, 0.00001);
    }

    @Test
    void testCalculatePnL_zeroProfitLoss() {
        double quantity = 10.0;
        double buyPrice = 50.0;
        double currentPrice = 50.0;

        double result = valuationService.calculatePnL(quantity, buyPrice, currentPrice);

        assertEquals(0.0, result, 0.00001);
    }
}
