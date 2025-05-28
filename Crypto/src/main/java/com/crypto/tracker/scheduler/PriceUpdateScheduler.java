package com.crypto.tracker.scheduler;

import com.crypto.tracker.model.CryptoHolding;
import com.crypto.tracker.repository.CryptoHoldingRepository;
import com.crypto.tracker.service.ExternalPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceUpdateScheduler {

    private final CryptoHoldingRepository holdingRepo;
    private final ExternalPriceService externalPriceService;

    @Scheduled(fixedRate = 600000)
    public void updateCryptoPrice() {
        List<CryptoHolding> allHoldings = holdingRepo.findAll();
        Set<String> symbols = allHoldings.stream()
                .map(CryptoHolding::getSymbol)
                .collect(Collectors.toSet());

        for (String symbol : symbols) {
            Double latestPrice = externalPriceService.getPriceBySymbol(symbol);
            if (latestPrice != null) {
                allHoldings.stream()
                        .filter(h -> h.getSymbol().equalsIgnoreCase(symbol))
                        .forEach(h -> h.setCurrentPrice(latestPrice));
            }
        }
        holdingRepo.saveAll(allHoldings);
    }
}
