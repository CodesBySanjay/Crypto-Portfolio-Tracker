package com.crypto.tracker.controller;

import com.crypto.tracker.dto.CryptoHoldingRequestDTO;
import com.crypto.tracker.dto.CryptoHoldingResponseDTO;
import com.crypto.tracker.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class CryptoHoldingController {

    private final PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<CryptoHoldingResponseDTO> addHolding(@RequestBody CryptoHoldingRequestDTO dto) {
        return ResponseEntity.ok(portfolioService.addHolding(dto));
    }

    @GetMapping
    public ResponseEntity<List<CryptoHoldingResponseDTO>> getMyHoldings() {
        return ResponseEntity.ok(portfolioService.getMyHoldings());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CryptoHoldingResponseDTO> updateHolding(
            @PathVariable Long id,
            @RequestBody CryptoHoldingRequestDTO dto
    ) {
        return ResponseEntity.ok(portfolioService.updateHolding(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHolding(@PathVariable Long id) {
        portfolioService.deleteHolding(id);
        return ResponseEntity.noContent().build();
    }
}