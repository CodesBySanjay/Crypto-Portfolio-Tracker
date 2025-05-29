package com.crypto.tracker.service;

import com.crypto.tracker.model.Alert;
import com.crypto.tracker.repository.AlertRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final ExternalPriceService externalPriceService;
    public AlertService(AlertRepository alertRepository, ExternalPriceService externalPriceService) {
        this.alertRepository = alertRepository;
        this.externalPriceService = externalPriceService;
    }

    public Alert createAlert(Long userId, String cryptoSymbol, Double targetPrice, String alertType) {
        Alert alert = new Alert();
        alert.setUserId(userId);
        alert.setCryptoSymbol(cryptoSymbol);
        alert.setTargetPrice(targetPrice);
        alert.setAlertType(alertType);
        alert.setTriggered(false);
        return alertRepository.save(alert);
    }

    @Scheduled(fixedDelay = 60000)
    public void checkAlerts() {
        List<Alert> alerts = alertRepository.findByTriggeredFalse();
        for (Alert alert : alerts) {
            Double currentPrice = externalPriceService.getCurrentPrice(alert.getCryptoSymbol());
            if (currentPrice != null) {
                if ("ABOVE".equalsIgnoreCase(alert.getAlertType()) && currentPrice >= alert.getTargetPrice()) {
                    alert.setTriggered(true);
                    alertRepository.save(alert);
                    notifyUser(alert, currentPrice);
                } else if ("BELOW".equalsIgnoreCase(alert.getAlertType()) && currentPrice <= alert.getTargetPrice()) {
                    alert.setTriggered(true);
                    alertRepository.save(alert);
                    notifyUser(alert, currentPrice);
                }
            }
        }
    }

    private void notifyUser(Alert alert, Double currentPrice) {

    }
}
