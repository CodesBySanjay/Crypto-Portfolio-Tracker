package com.crypto.tracker.dto;

public class CreateAlertRequest{

    private String cryptoSymbol;

    private Double targetPrice;

    private String alertType;

    public CreateAlertRequest() {}

    public String getCryptoSymbol() { return cryptoSymbol; }

    public void setCryptoSymbol(String cryptoSymbol) { this.cryptoSymbol = cryptoSymbol; }

    public Double getTargetPrice() { return targetPrice; }

    public void setTargetPrice(Double targetPrice) { this.targetPrice = targetPrice; }

    public String getAlertType() { return alertType; }

    public void setAlertType(String alertType) { this.alertType = alertType; }
}
