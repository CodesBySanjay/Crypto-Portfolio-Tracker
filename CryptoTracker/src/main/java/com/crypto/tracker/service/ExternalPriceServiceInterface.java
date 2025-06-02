package com.crypto.tracker.service;


import com.crypto.tracker.dto.CryptoMarketDTO;

import java.util.List;

public interface ExternalPriceServiceInterface {

    Double getPriceBySymbol(String symbol);

    List<CryptoMarketDTO> fetchAllPrices();

    void updatePrices();
}
