package com.crypto.tracker.Repository;

import com.crypto.tracker.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByTriggeredFalse();
}
