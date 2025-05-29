package com.crypto.tracker.scheduler;

import com.crypto.tracker.model.CryptoHolding;
import com.crypto.tracker.repository.CryptoHoldingRepository;
import com.crypto.tracker.service.ExternalPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceUpdateScheduler {

    private final CryptoHoldingRepository holdingRepo;
    private final ExternalPriceService externalPriceService;

    @Scheduled(fixedRate = 600_000)
    public void updateCryptoPrice() {
        List<CryptoHolding> holdings = holdingRepo.findAll();

        holdings.forEach(holding -> {
            Double latestPrice = externalPriceService.getPriceBySymbol(holding.getSymbol());
            if (latestPrice != null) {
                holding.setCurrentPrice(latestPrice);
            }
        });
        holdingRepo.saveAll(holdings);
    }
}
