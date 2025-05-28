package com.example.crypto_tracker.service;

import com.example.crypto_tracker.dto.CryptoMarketDTO;

import java.util.List;

public interface ExternalPriceInterface {

    Double getPriceBySymbol(String symbol);

    String getNameBySymbol(String symbol);

    List<CryptoMarketDTO> fetchAllPrices();

    void updatePrices();
}
