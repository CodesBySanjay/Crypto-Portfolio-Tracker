package com.crypto.tracker.repository;

import com.crypto.tracker.model.CryptoHolding;
import com.crypto.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CryptoHoldingRepository extends JpaRepository<CryptoHolding, Long> {
    List<CryptoHolding> findByUser(User user);
}
