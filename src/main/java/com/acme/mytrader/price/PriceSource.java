package com.acme.mytrader.price;

import com.acme.mytrader.strategy.PriceListener;

public interface PriceSource {
    void addPriceListener(PriceListener listener);
    void removePriceListener(PriceListener listener);
}
