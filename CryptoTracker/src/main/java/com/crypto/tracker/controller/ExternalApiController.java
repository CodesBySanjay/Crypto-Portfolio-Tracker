package com.crypto.tracker.controller;

import com.crypto.tracker.dto.CryptoMarketDTO;
import com.crypto.tracker.service.ExternalPriceServiceInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/realtime")
@Tag(name= "RealTime Data", description =  "Can see real time market Updates")
public class ExternalApiController {

    @Autowired
    ExternalPriceServiceInterface externalPriceServiceInterface;

    @GetMapping("/allprices")
    public List<CryptoMarketDTO> getAllPrices() {
        return externalPriceServiceInterface.fetchAllPrices();
    }


    @GetMapping("/price/{symbol}")
    public Double getCryptoPriceBySymbol(@PathVariable String symbol) {
        return externalPriceServiceInterface.getPriceBySymbol(symbol);
    }

}
