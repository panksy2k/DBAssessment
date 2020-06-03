package com.acme.mytrader.concurrent;

import com.acme.mytrader.dto.StockInfo;
import com.acme.mytrader.execution.TradeExecutor;
import com.acme.mytrader.price.StateMachine;
import com.acme.mytrader.strategy.TradingStrategy;

/**
 * This is the main interface which would accept messages from the market -- and hence
 * has the state machine {@code StateMachine} and the trade strategies attached to it (Observable) -
 * {@link TradeStrategy} such that when the inquiry comes then the same is sent to the
 * state machine in order to handle the flow of execution.
 *
 * <ul>The interface also maintains the final ledger in terms of --
 *  <li>how many inquiries came;</li>
 *  <li>how many deals are executed - depending on the strategy/rule(s) registered etc.</li>
 * </ul>
 *
 */
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
