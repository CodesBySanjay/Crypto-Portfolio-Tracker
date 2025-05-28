package com.crypto.tracker.service;

import com.crypto.tracker.dto.CryptoHoldingRequestDTO;
import com.crypto.tracker.dto.CryptoHoldingResponseDTO;
import com.crypto.tracker.model.CryptoHolding;
import com.crypto.tracker.model.User;
import com.crypto.tracker.repository.CryptoHoldingRepository;
import com.crypto.tracker.security.SessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService implements IPortfolioService {

    private final CryptoHoldingRepository holdingRepo;
    private final SessionStore sessionStore;
    private final ExternalPriceService externalPriceService;
    private final ValuationService valuationService;

    private User getCurrentUser(String sessionId) {
        User user = sessionStore.get(sessionId);
        if (user == null) {
            throw new RuntimeException("Invalid or expired session.");
        }
        return user;
    }


    @Override
    public CryptoHoldingResponseDTO addHolding(String sessionId, CryptoHoldingRequestDTO dto) {
        User user = getCurrentUser(sessionId);

        if (!user.getRole().name().equalsIgnoreCase("USER")) {
            throw new RuntimeException("Only users with role 'USER' can perform this action.");
        }

        if (dto.getSymbol() == null || dto.getSymbol().isBlank()) {
            throw new RuntimeException("Symbol must not be null or empty");
        }

        String coinName = externalPriceService.getNameBySymbol(dto.getSymbol());
        Double currentPrice = externalPriceService.getPriceBySymbol(dto.getSymbol());

        if(coinName == null || currentPrice == null)
            throw new RuntimeException("Symbol not found or invalid");

        CryptoHolding holding = new CryptoHolding();
        holding.setCoinName(coinName);
        holding.setSymbol(dto.getSymbol().toUpperCase());
        holding.setQuantityHeld(dto.getQuantityHeld());
        holding.setBuyPrice(currentPrice);
        holding.setBuyDate(LocalDateTime.now());
        holding.setCurrentPrice(currentPrice);
        holding.setUser(user);

        CryptoHolding saved = holdingRepo.save(holding);
        return convertToDTO(saved, 1);
    }

    @Override
    public List<CryptoHoldingResponseDTO> getMyHoldings(String sessionId) {
        User user = getCurrentUser(sessionId);
        List<CryptoHolding> holdings = holdingRepo.findByUser(user);
        final int[] serial = {1};

        return holdings.stream()
                .map(h -> {
                    Double currentPrice = externalPriceService.getPriceBySymbol(h.getSymbol());
                    if (currentPrice != null && !currentPrice.equals(h.getCurrentPrice())) {
                        h.setCurrentPrice(currentPrice);
                        holdingRepo.save(h);
                    }
                    return convertToDTO(h, serial[0]++);
                })
                .collect(Collectors.toList());

    }

    @Override
    public void deleteHolding(String sessionId, Long holdingId) {
        User user = getCurrentUser(sessionId);

        CryptoHolding holding = holdingRepo.findById(holdingId)
                .orElseThrow(() -> new RuntimeException("Holding not found"));

        String holdingOwnerUsername = holding.getUser().getName();

        if (!holdingOwnerUsername.equals(user.getName())) {
            throw new RuntimeException("Holding does not belong to the current user");
        }

        holdingRepo.delete(holding);
    }


    private CryptoHoldingResponseDTO convertToDTO(CryptoHolding holding, int serial) {
        CryptoHoldingResponseDTO dto = new CryptoHoldingResponseDTO();
        dto.setId(holding.getId());
        dto.setSerialNumber(serial);
        dto.setCoinName(holding.getCoinName());
        dto.setSymbol(holding.getSymbol());
        dto.setQuantityHeld(holding.getQuantityHeld());
        dto.setBuyPrice(holding.getBuyPrice());
        dto.setBuyDate(holding.getBuyDate());
        dto.setCurrentPrice(holding.getCurrentPrice());
        dto.setProfitLoss(valuationService.calculatePnL(
                holding.getQuantityHeld(),
                holding.getBuyPrice(),
                holding.getCurrentPrice() != null ? holding.getCurrentPrice() : 0.0));
        return dto;
    }
}
