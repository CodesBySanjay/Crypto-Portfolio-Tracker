package com.crypto.tracker.dto;

import com.crypto.tracker.entity.Direction;
import com.crypto.tracker.entity.Status;
import java.time.LocalDateTime;

public class AlertResponse {
    private Long id;
    private String symbol;
    private Double triggerPrice;
    private Double profitLoss;
    private Direction direction;
    private Status status;
    private LocalDateTime triggeredAt;

    public AlertResponse() {}

    public AlertResponse(Long id, String symbol, Double triggerPrice, Double profitLoss, Direction direction, Status status, LocalDateTime triggeredAt) {
        this.id = id;
        this.symbol = symbol;
        this.triggerPrice = triggerPrice;
        this.profitLoss = profitLoss;
        this.direction = direction;
        this.status = status;
        this.triggeredAt = triggeredAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public Double getTriggerPrice() { return triggerPrice; }
    public void setTriggerPrice(Double triggerPrice) { this.triggerPrice = triggerPrice; }

    public Double getProfitLoss() { return profitLoss; }
    public void setProfitLoss(Double profitLoss) { this.profitLoss = profitLoss; }

    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = direction; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getTriggeredAt() { return triggeredAt; }
    public void setTriggeredAt(LocalDateTime triggeredAt) { this.triggeredAt = triggeredAt; }
}
