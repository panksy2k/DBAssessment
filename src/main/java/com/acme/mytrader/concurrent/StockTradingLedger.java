package com.acme.mytrader.concurrent;

import com.acme.mytrader.dto.StockInfo;
import com.acme.mytrader.execution.TradeExecutor;
import com.acme.mytrader.price.StateMachine;
import com.acme.mytrader.strategy.TradingStrategy;

public class StockTradingLedger implements StockEnquiry {
    private final StateMachine stateMachine;
    private StatsLedger statsLedger;

    public StockTradingLedger() {
        this.stateMachine = new StateMachine();
        addStrategy();
    }

    private void addStrategy() {
        this.statsLedger = new TradingStrategy(this.stateMachine, new TradeExecutor());
    }

    @Override
    public void addEnquiry(StockInfo stockDetails) {
        this.stateMachine.monitorStockPrice(stockDetails);
    }

    @Override
    public StatsLedger getStatistics() {
        return this.statsLedger;
    }
}
