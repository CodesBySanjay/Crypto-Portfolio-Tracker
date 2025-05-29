package com.crypto.tracker.controller;

import com.crypto.tracker.dto.CreateAlertRequest;
import com.crypto.tracker.model.Alert;
import com.crypto.tracker.service.AlertService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
@Tag(name= "Alerts", description = " Alert Management for User ")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public ResponseEntity<Alert> createAlert(@RequestBody CreateAlertRequest dto,
                                             @RequestHeader("userId") Long userId) {
        Alert alert = alertService.createAlert(userId, dto.getCryptoSymbol(),
                dto.getTargetPrice(), dto.getAlertType());
        return ResponseEntity.ok(alert);
    }
}
