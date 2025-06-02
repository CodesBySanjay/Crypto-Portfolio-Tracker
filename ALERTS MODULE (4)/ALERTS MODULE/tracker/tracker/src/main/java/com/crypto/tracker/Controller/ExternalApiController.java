package com.crypto.tracker.controller;

import com.crypto.tracker.dto.CryptoMarketDTO;
import com.crypto.tracker.service.ExternalPriceService;
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
        return externalPriceService.fetchMockPrices();
    }

    @GetMapping("/price/{symbol}")
    public Double getCryptoPriceBySymbol(@PathVariable String symbol) {
        return externalPriceService.getPriceBySymbol(symbol);
    }

    @GetMapping("/name/{symbol}")
    public String getCryptoNameBySymbol(@PathVariable String symbol) {
        return externalPriceService.getNameBySymbol(symbol);
    }
}
