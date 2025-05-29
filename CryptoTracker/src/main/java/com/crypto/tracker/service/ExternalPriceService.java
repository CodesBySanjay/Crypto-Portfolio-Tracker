package com.crypto.tracker.service;

import com.crypto.tracker.dto.CryptoMarketDTO;
import com.crypto.tracker.exception.InvalidInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ExternalPriceService implements ExternalPriceServiceInterface {

    private final String apiUrl =
            "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1&x_cg_demo_api_key=CG-QmqwpgR3nyctfMWJ2v9PWycH";

    private final RestTemplate restTemplate = new RestTemplate();

    public Double getCurrentPrice(String cryptoSymbol)
    {
        return getPriceBySymbol(cryptoSymbol);
    }
    @Override
    public Double getPriceBySymbol(String symbol) {
        try
        {
            CryptoMarketDTO[] coins = restTemplate.getForObject(apiUrl, CryptoMarketDTO[].class);
            return Arrays.stream(coins)
                    .filter(coin -> coin.getSymbol().equalsIgnoreCase(symbol))
                    .map(CryptoMarketDTO::getCurrentPrice)
                    .findFirst()
                    .orElse(null);
        }
        catch (Exception e)
        {
            System.err.println("Error fetching price for " + symbol + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getNameBySymbol(String symbol) {
        try
        {
            CryptoMarketDTO[] coins = restTemplate.getForObject(apiUrl, CryptoMarketDTO[].class);
            if (coins == null)
                return null;

            return Arrays.stream(coins)
                    .filter(coin ->  coin.getSymbol().equalsIgnoreCase(symbol))
                    .map(CryptoMarketDTO::getName)
                    .findFirst()
                    .orElse(null);
        }
        catch (Exception e)
        {
            System.err.println("Error fetching name for " + symbol + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<CryptoMarketDTO> fetchAllPrices() {
        try
        {
            ResponseEntity<CryptoMarketDTO[]> response =
                    restTemplate.getForEntity(apiUrl, CryptoMarketDTO[].class);
            return Arrays.asList(response.getBody() != null ? response.getBody() : new CryptoMarketDTO[0]);
        }
        catch (Exception e)
        {
            System.err.println("Error fetching mock prices: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public String getSymbolByName(String name) {
        CryptoMarketDTO[] coins = restTemplate.getForObject(apiUrl, CryptoMarketDTO[].class);
        if (coins == null) throw new InvalidInputException("No market data found");

        return Arrays.stream(coins)
                .filter(coin -> name.equalsIgnoreCase(coin.getName()))
                .map(CryptoMarketDTO::getSymbol)
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("Invalid currency name! Check the spelling"));
    }

    @Override
    public void updatePrices() {
        List<CryptoMarketDTO> marketData = fetchAllPrices();
    }

    @Scheduled(fixedRate = 120000)
    public void scheduledPriceUpdate() {
        updatePrices();
    }
}
