package com.example.cryptoportfoliotracker.controller;

import com.example.crypto_tracker.dto.CryptoMarketDTO;
import com.example.cryptoportfoliotracker.service.ExternalPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/external")
public class ExternalApiController {

    @Autowired
    private ExternalPriceService externalPriceService;

    @GetMapping("/allprices")
    public List<CryptoMarketDTO> getAllPrices() {
        return externalPriceService.fetchAllPrices();
    }


    @GetMapping("/price/{symbol}")
    public Double getCryptoPriceBySymbol(@PathVariable String symbol) {
        return externalPriceService.getPriceBySymbol(symbol);
    }

    @GetMapping("/name/{symbol}")
    public String getCryptoNamebySymbol(@PathVariable String symbol) {
        return externalPriceService.getNameBySymbol(symbol);
    }

}
