package com.acme.mytrader.strategy;

import com.acme.mytrader.concurrent.StatsLedger;
import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.StateMachine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy implements PriceListener, StatsLedger {
    private Map<String, LongAdder> buyOrders = new ConcurrentHashMap<>();

    private LongAdder totalRecordCount = new LongAdder();

    private final ExecutionService executionService;

    public TradingStrategy(StateMachine trader, ExecutionService executionService) {
        this.executionService = executionService;
        trader.addPriceListener(this);
    }

    @Override
    public void priceUpdate(String security, double price) {
        totalRecordCount.increment();
        BigDecimal inquiryPrice = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);

        System.out.println("\nRule for -> " + security + "\tprice " + price + "\tThread: " + Thread.currentThread().getName());

        if (PriceListener.getIBMSecurity().equalsIgnoreCase(security) &&
                inquiryPrice.compareTo(PriceListener.getStrikePrice()) < 0) {

            System.out.println("\nIBM StrikePrice Rule met -> " + security + "\tprice " + price + "\tThread: " + Thread.currentThread().getName());

            this.executionService.buy(security, price, 100);

            buyOrders.computeIfAbsent("BUY", k -> new LongAdder()).increment();
        }
    }

    @Override
    public Integer getRecordCount() {
        return totalRecordCount.intValue();
    }

    @Override
    public Integer getBuyCount() {
        return buyOrders.get("BUY").intValue();
    }
}
