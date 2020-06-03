package com.acme.mytrader.execution;

/**
 * The Trade executor that would execute a trade - buy or sell.
 */
public class TradeExecutor implements ExecutionService {
    private static final String RED = "\u001b[31m";
    private static final String GREEN = "\u001b[32m";
    private static final String RESET = "\u001b[0m";

    @Override
    public void buy(String security, double price, int volume) {
        System.out.printf("%sBought %s at %f in %d%s", GREEN, security, price, volume, RESET);
    }

    @Override
    public void sell(String security, double price, int volume) {
        System.out.printf("%sSold %s at %f in %d", RED, security, price, volume);
    }
}
