package com.crypto.tracker.service;

import com.crypto.tracker.dto.CryptoHoldingRequestDTO;
import com.crypto.tracker.dto.CryptoHoldingResponseDTO;

import java.util.List;

public interface IPortfolioService
{
    CryptoHoldingResponseDTO addHolding(String sessionId,CryptoHoldingRequestDTO dto);
    List<CryptoHoldingResponseDTO> getMyHoldings(String sessionId);
    void deleteHolding(String sessionId, Long holdingId);
}
