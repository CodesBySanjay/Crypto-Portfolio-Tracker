package com.crypto.tracker.controller;

import com.crypto.tracker.dto.CryptoHoldingRequestDTO;
import com.crypto.tracker.dto.CryptoHoldingResponseDTO;
import com.crypto.tracker.service.IPortfolioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
@Tag(name= "Buy/Sell Crypto", description = "User could buy/sell crypto")
public class PortfolioController {

    private final IPortfolioService portfolioService;

    @PostMapping("/buy")
    public ResponseEntity<CryptoHoldingResponseDTO> addHolding(@RequestParam("code") String sessionId, @RequestBody CryptoHoldingRequestDTO dto)
    {
        CryptoHoldingResponseDTO response = portfolioService.addHolding(sessionId, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view")
    public ResponseEntity<List<CryptoHoldingResponseDTO>> getMyHoldings(@RequestParam("code") String sessionId)
    {
        List<CryptoHoldingResponseDTO> holdings = portfolioService.getMyHoldings(sessionId);
        return ResponseEntity.ok(holdings);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHolding(
            @PathVariable Long id,
            @RequestParam("code") String sessionId) {
        portfolioService.deleteHolding(sessionId, id);
        return ResponseEntity.noContent().build();
    }

}
