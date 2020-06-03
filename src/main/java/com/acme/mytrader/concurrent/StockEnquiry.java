package com.acme.mytrader.concurrent;

import com.acme.mytrader.dto.StockInfo;

public interface StockEnquiry {
    void addEnquiry(StockInfo stockDetails);
    StatsLedger getStatistics();
}