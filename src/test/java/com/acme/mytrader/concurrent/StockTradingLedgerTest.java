package com.acme.mytrader.concurrent;

import com.acme.mytrader.dto.StockInfo;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class StockTradingLedgerTest {
    private static final int NUMBER_OF_IDENTITIES = 100;
    private ExecutorService testPool = Executors.newWorkStealingPool();

    @Test
    public void whenPriceEnquiryComes_AndMatchesRule_ExecuteOrder() throws Exception {
        ///given

        //when
        StockEnquiry enquiry = new StockTradingLedger();
        testEnquiries(enquiry);

        //then
        assertEquals(10, enquiry.getStatistics().getBuyCount().intValue());
    }

    @Test
    public void whenPriceEnquiryComes_AndMatchesNoRule_DoNotExecuteOrder() throws Exception {
        //when
        StockEnquiry enquiry = new StockTradingLedger();
        testEnquiries(enquiry);

        //then
        assertEquals(90, enquiry.getStatistics().getRecordCount() - 10);
    }

    private void testEnquiries(StockEnquiry enquiry) throws Exception {
        CountDownLatch cdl = new CountDownLatch(NUMBER_OF_IDENTITIES);

        List<StockInfo> allIBMStocksAboveStrikePrice = IntStream.rangeClosed(1, 10).mapToObj(i -> new StockInfo("IBM", 55.0 + i, 100)).collect(Collectors.toList());
        allIBMStocksAboveStrikePrice.stream().forEach(l -> {
            testPool.submit(() -> {
                enquiry.addEnquiry(l);
                cdl.countDown();
            });
        });

        List<StockInfo> allIBMStocksBelowStrikePrice = IntStream.rangeClosed(1, 10).mapToObj(i -> new StockInfo("IBM", 54.0 - i, 100)).collect(Collectors.toList());
        allIBMStocksBelowStrikePrice.stream().forEach(l -> {
            testPool.submit(() -> {
                enquiry.addEnquiry(l);
                cdl.countDown();
            });
        });

        String[] randomSecurities = {"AAPL", "MS", "GOOG", "LYDS", "JPMC", "BAML", "GS", "DB", "HFX", "HBC"};

        for (int i = 0; i < NUMBER_OF_IDENTITIES - (allIBMStocksAboveStrikePrice.size() + allIBMStocksBelowStrikePrice.size()); i++) {
            testPool.submit(() -> {
                enquiry.addEnquiry(new StockInfo(randomSecurities[new Random().nextInt(randomSecurities.length - 1)], Double.valueOf(new Random().nextInt(100)), 100));
                cdl.countDown();
            });
        }

        cdl.await();
    }
}
