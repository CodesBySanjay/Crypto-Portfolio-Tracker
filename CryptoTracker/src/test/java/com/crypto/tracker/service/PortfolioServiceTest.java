package com.crypto.tracker.service;

import com.crypto.tracker.dto.CryptoHoldingRequestDTO;
import com.crypto.tracker.dto.CryptoHoldingResponseDTO;
import com.crypto.tracker.model.CryptoHolding;
import com.crypto.tracker.model.Role;
import com.crypto.tracker.model.User;
import com.crypto.tracker.repository.CryptoHoldingRepository;
import com.crypto.tracker.security.SessionStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PortfolioServiceTest {

    @InjectMocks
    PortfolioService portfolioService;

    @Mock
    CryptoHoldingRepository holdingRepo;

    @Mock
    SessionStore sessionStore;

    @Mock
    ExternalPriceServiceInterface priceService;

    @Mock
    ValuationService valuationService;

    User user;
    String sessionId = "abc";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUser_id(1L);
        user.setSessionCode(sessionId);
        user.setRole(Role.USER);
        when(sessionStore.get(sessionId)).thenReturn(user);
    }

    @Test
    void addHolding_success() {
        CryptoHoldingRequestDTO req = new CryptoHoldingRequestDTO();
        req.setSymbol("BTC");
        req.setQuantityHeld(1.0);

        when(priceService.getNameBySymbol("BTC")).thenReturn("Bitcoin");
        when(priceService.getPriceBySymbol("BTC")).thenReturn(30000.0);
        when(valuationService.calculatePnL(1.0, 30000.0, 30000.0)).thenReturn(0.0);

        CryptoHolding saved = CryptoHolding.builder()
                .id(1L).coinName("Bitcoin").symbol("BTC").quantityHeld(1.0)
                .buyPrice(30000.0).currentPrice(30000.0).buyDate(LocalDateTime.now())
                .profitLoss(0.0).user(user).build();

        when(holdingRepo.save(any())).thenReturn(saved);

        CryptoHoldingResponseDTO res = portfolioService.addHolding(sessionId, req);

        assertNotNull(res);
        assertEquals("Bitcoin", res.getCoinName());
        assertEquals("BTC", res.getSymbol());
        assertEquals(1.0, res.getQuantityHeld());
    }

    @Test
    void getMyHoldings_success() {
        CryptoHolding holding = CryptoHolding.builder()
                .id(1L).coinName("Bitcoin").symbol("BTC").quantityHeld(1.0)
                .buyPrice(30000.0).currentPrice(31000.0).buyDate(LocalDateTime.now())
                .profitLoss(1000.0).user(user).build();

        when(holdingRepo.findByUser(user)).thenReturn(List.of(holding));
        when(priceService.getPriceBySymbol("BTC")).thenReturn(31000.0);
        when(valuationService.calculatePnL(1.0, 30000.0, 31000.0)).thenReturn(1000.0);

        List<CryptoHoldingResponseDTO> list = portfolioService.getMyHoldings(sessionId);

        assertEquals(1, list.size());
        assertEquals("BTC", list.get(0).getSymbol());
    }

    @Test
    void deleteHolding_success() {
        CryptoHolding holding = CryptoHolding.builder()
                .id(2L).coinName("ETH").symbol("ETH").quantityHeld(2.0)
                .buyPrice(1500.0).currentPrice(1600.0).buyDate(LocalDateTime.now())
                .profitLoss(200.0).user(user).build();

        when(holdingRepo.findById(2L)).thenReturn(Optional.of(holding));

        portfolioService.deleteHolding(sessionId, 2L);

        verify(holdingRepo).delete(holding);
    }

    @Test
    void addHolding_invalidSession_throws() {
        when(sessionStore.get("bad")).thenReturn(null);

        CryptoHoldingRequestDTO req = new CryptoHoldingRequestDTO();
        req.setSymbol("BTC");
        req.setQuantityHeld(1.0);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> portfolioService.addHolding("bad", req));

        assertEquals("Invalid or expired session.", ex.getMessage());
    }
}