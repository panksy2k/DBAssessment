package com.acme.mytrader.strategy;

import java.math.BigDecimal;

public interface PriceListener {

    static String getIBMSecurity() {
        return "IBM";
    }

    static BigDecimal getStrikePrice() {
        return BigDecimal.valueOf(55.00D);
    }

    void priceUpdate(String security, double price);
}
