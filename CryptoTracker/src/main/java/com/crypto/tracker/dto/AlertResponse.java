package com.crypto.tracker.dto;

import com.crypto.tracker.model.Direction;
import com.crypto.tracker.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponse {
    private Long id;
    private String symbol;
    private Double triggerPrice;
    private Double profitLoss;
    private Direction direction;
    private Status status;
    private LocalDateTime triggeredAt;
}
