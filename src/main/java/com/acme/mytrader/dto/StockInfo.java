package com.acme.mytrader.dto;

public class StockInfo {
    private final String security;
    private final double price;
    private final int volume;

    public StockInfo(String security, double price, int volume) {
        this.security = security;
        this.price = price;
        this.volume = volume;
    }

    public String getSecurity() {
        return security;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }
}
