package com.crypto.tracker.service;

import com.crypto.tracker.model.Alert;
import com.crypto.tracker.repository.AlertRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class AlertServiceTest {

    @Test
    void checkAlertsTriggersAndSavesAlertsAbove() {
        AlertRepository alertRepo = mock(AlertRepository.class);
        ExternalPriceService priceService = mock(ExternalPriceService.class);
        AlertService alertService = new AlertService(alertRepo, priceService);

        Alert alert = new Alert();
        alert.setCryptoSymbol("ETH");
        alert.setTargetPrice(2000.0);
        alert.setAlertType("ABOVE");
        alert.setTriggered(false);

        when(alertRepo.findByTriggeredFalse()).thenReturn(List.of(alert));
        when(priceService.getCurrentPrice("ETH")).thenReturn(2100.0);

        alertService.checkAlerts();

        verify(alertRepo, times(1)).save(any());
    }
}