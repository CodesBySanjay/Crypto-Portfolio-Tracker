package com.crypto.tracker.Controller;

import com.crypto.tracker.dto.CreateAlertRequest;
import com.crypto.tracker.entity.Alert;
import com.crypto.tracker.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
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
