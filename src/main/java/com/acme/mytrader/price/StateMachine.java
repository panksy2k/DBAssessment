package com.acme.mytrader.price;

import com.acme.mytrader.dto.StockInfo;
import com.acme.mytrader.strategy.PriceListener;

import java.util.ArrayList;
import java.util.List;

public class StateMachine implements PriceSource {
    private List<PriceListener> observers = new ArrayList<>();

    @Override
    public void addPriceListener(PriceListener listener) {
        observers.add(listener);
    }

    @Override
    public void removePriceListener(PriceListener listener) {
        observers.remove(listener);
    }

    public void monitorStockPrice(StockInfo stock) {
        notifyListener(stock);
    }

    private void notifyListener(final StockInfo orderDetails) {
        for(PriceListener listener : observers) {
            listener.priceUpdate(orderDetails.getSecurity(), orderDetails.getPrice());
        }
    }
}
