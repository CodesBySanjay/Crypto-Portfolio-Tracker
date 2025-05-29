package com.crypto.tracker.scheduler;

import com.crypto.tracker.model.CryptoHolding;
import com.crypto.tracker.repository.CryptoHoldingRepository;
import com.crypto.tracker.service.ExternalPriceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class PriceUpdateSchedulerTest {

    @Mock
    CryptoHoldingRepository holdingRepo;

    @Mock
    ExternalPriceService externalPriceService;

    @InjectMocks
    PriceUpdateScheduler scheduler;

    public PriceUpdateSchedulerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateCryptoPrice() {
        CryptoHolding h = new CryptoHolding();
        h.setSymbol("BTC");
        when(holdingRepo.findAll()).thenReturn(List.of(h));
        when(externalPriceService.getPriceBySymbol("BTC")).thenReturn(1000.0);

        scheduler.updateCryptoPrice();

        verify(holdingRepo).saveAll(List.of(h));
    }
}