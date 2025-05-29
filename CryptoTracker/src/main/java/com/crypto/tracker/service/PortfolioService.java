package com.crypto.tracker.service;

import com.crypto.tracker.dto.CryptoHoldingRequestDTO;
import com.crypto.tracker.dto.CryptoHoldingResponseDTO;
import com.crypto.tracker.model.CryptoHolding;
import com.crypto.tracker.model.User;
import com.crypto.tracker.repository.CryptoHoldingRepository;
import com.crypto.tracker.security.SessionStore;
import org.springframework.stereotype.Service;
import com.crypto.tracker.exception.InvalidSessionException;
import com.crypto.tracker.exception.ResourceNotFoundException;
import com.crypto.tracker.exception.AccessDeniedException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PortfolioService implements IPortfolioService {

    private final CryptoHoldingRepository holdingRepo;
    private final SessionStore sessionStore;
    private final ExternalPriceServiceInterface externalPriceInterface;
    private final ValuationService valuationService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PortfolioService(CryptoHoldingRepository holdingRepo,
                            SessionStore sessionStore,
                            ExternalPriceServiceInterface externalPriceInterface,
                            ValuationService valuationService) {
        this.holdingRepo = holdingRepo;
        this.sessionStore = sessionStore;
        this.externalPriceInterface = externalPriceInterface;
        this.valuationService = valuationService;
    }

    public CryptoHoldingResponseDTO addHolding(String sessionId, CryptoHoldingRequestDTO request) {
        User user = sessionStore.get(sessionId);
        if (user == null) throw new InvalidSessionException("Invalid or expired session");

        String coinName = externalPriceInterface.getNameBySymbol(request.getSymbol());
        Double currentPrice = externalPriceInterface.getPriceBySymbol(request.getSymbol());

        CryptoHolding holding = CryptoHolding.builder()
                .coinName(coinName)
                .symbol(request.getSymbol())
                .quantityHeld(request.getQuantityHeld())
                .buyPrice(currentPrice)
                .currentPrice(currentPrice)
                .buyDate(LocalDateTime.now())
                .profitLoss(valuationService.calculatePnL(request.getQuantityHeld(), currentPrice, currentPrice))
                .user(user)
                .build();

        CryptoHolding saved = holdingRepo.save(holding);

        return mapToResponse(saved);
    }

    public List<CryptoHoldingResponseDTO> getMyHoldings(String sessionId) {
        User user = sessionStore.get(sessionId);
        if (user == null) throw new InvalidSessionException("Invalid or expired session");

        List<CryptoHolding> holdings = holdingRepo.findByUser(user);

        return holdings.stream().map(holding -> {
            Double currentPrice = externalPriceInterface.getPriceBySymbol(holding.getSymbol());
            Double pnl = valuationService.calculatePnL(holding.getQuantityHeld(), holding.getBuyPrice(), currentPrice);
            holding.setCurrentPrice(currentPrice);
            holding.setProfitLoss(pnl);
            holdingRepo.save(holding);
            return mapToResponse(holding);
        }).collect(Collectors.toList());
    }

    public void deleteHolding(String sessionId, Long holdingId) {
        User user = sessionStore.get(sessionId);
        if (user == null) throw new InvalidSessionException("Invalid or expired session");

        CryptoHolding holding = holdingRepo.findById(holdingId)
                .orElseThrow(() -> new ResourceNotFoundException("Holding not found"));

        String holdingOwnerUsername = holding.getUser() != null ? holding.getUser().getEmail() : null;
        String currentUsername = user.getEmail();

        if (!Objects.equals(holdingOwnerUsername, currentUsername)) {
            throw new AccessDeniedException("Not authorized to delete this holding");
        }

        holdingRepo.delete(holding);
    }

    private CryptoHoldingResponseDTO mapToResponse(CryptoHolding holding) {
        CryptoHoldingResponseDTO response = new CryptoHoldingResponseDTO();
        response.setId(holding.getId());
        response.setCoinName(holding.getCoinName());
        response.setSymbol(holding.getSymbol());
        response.setQuantityHeld(holding.getQuantityHeld());
        response.setBuyPrice(holding.getBuyPrice());
        response.setCurrentPrice(holding.getCurrentPrice());
        response.setBuyDate(holding.getBuyDate() != null ? holding.getBuyDate().format(formatter) : null);
        response.setProfitLoss(holding.getProfitLoss());
        return response;
    }
}