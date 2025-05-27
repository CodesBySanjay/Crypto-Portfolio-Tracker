package com.example.cryptoportfoliotracker.repository;

import com.example.cryptoportfoliotracker.entity.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoPriceRepository extends  JpaRepository<CryptoPrice, String> {
}
