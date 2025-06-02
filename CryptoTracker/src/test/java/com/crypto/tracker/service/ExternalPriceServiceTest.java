package com.crypto.tracker.service;

import com.crypto.tracker.dto.CryptoMarketDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExternalPriceServiceTest {

    @Test
    void getPriceBySymbol_returnsPrice() {
        RestTemplate rest = mock(RestTemplate.class);
        ExternalPriceService service = new ExternalPriceService();
        setRestTemplate(service, rest);

        CryptoMarketDTO coin = new CryptoMarketDTO();
        coin.setSymbol("btc");
        coin.setCurrentPrice(10000.0);

        when(rest.getForObject(anyString(), eq(CryptoMarketDTO[].class)))
                .thenReturn(new CryptoMarketDTO[]{coin});

        Double price = service.getPriceBySymbol("btc");
        assertEquals(10000.0, price);
    }

    @Test
    void fetchAllPrices_returnsList() {
        RestTemplate rest = mock(RestTemplate.class);
        ExternalPriceService service = new ExternalPriceService();
        setRestTemplate(service, rest);

        CryptoMarketDTO coin1 = new CryptoMarketDTO();
        CryptoMarketDTO coin2 = new CryptoMarketDTO();

        CryptoMarketDTO[] arr = new CryptoMarketDTO[]{coin1, coin2};

        when(rest.getForEntity(anyString(), eq(CryptoMarketDTO[].class)))
                .thenReturn(ResponseEntity.ok(arr));

        List<CryptoMarketDTO> list = service.fetchAllPrices();
        assertEquals(2, list.size());
    }

    private void setRestTemplate(ExternalPriceService service, RestTemplate rest) {
        try {
            var field = ExternalPriceService.class.getDeclaredField("restTemplate");
            field.setAccessible(true);
            field.set(service, rest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}