package com.acme.mytrader.price;

import com.acme.mytrader.dto.StockInfo;
import com.acme.mytrader.strategy.PriceListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the central core component that receives messages from the market {@code StockInfo} and use
 * Observer pattern (Observer) to register/notify the interested listeners who would take appropriate actions
 * such as striking a deal (execute an order) etc.
 *
 * There could be more than one listeners who could register themselves with the state machine.
 *
 */
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
