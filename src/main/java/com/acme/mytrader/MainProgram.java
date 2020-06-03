package com.acme.mytrader;

import com.acme.mytrader.dto.StockInfo;
import com.acme.mytrader.execution.TradeExecutor;
import com.acme.mytrader.strategy.PriceListener;
import com.acme.mytrader.price.StateMachine;
import com.acme.mytrader.strategy.TradingStrategy;

public class MainProgram {

    public static void main(String[] args) {
        StateMachine trader = new StateMachine();
        PriceListener priceListener = new TradingStrategy(trader, new TradeExecutor());


        trader.monitorStockPrice(new StockInfo("APL", 126.99, 100));
        trader.monitorStockPrice(new StockInfo("MS", 116.99, 100));
        trader.monitorStockPrice(new StockInfo("IBM", 52.99, 100));
        trader.monitorStockPrice(new StockInfo("IBM", 55.98, 100));
        trader.monitorStockPrice(new StockInfo("GOOG", 75.99, 100));

    }
}
