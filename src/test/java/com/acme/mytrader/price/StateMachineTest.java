package com.acme.mytrader.price;

import com.acme.mytrader.dto.StockInfo;
import com.acme.mytrader.strategy.PriceListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StateMachineTest {
    @Mock
    private PriceListener mockedPriceListener;

    @Test
    public void whenListenerIsRegistered_notifyEvents() {
        //given

        //when
        StateMachine SUT = new StateMachine();
        SUT.addPriceListener(mockedPriceListener);
        SUT.monitorStockPrice(new StockInfo("ISIN", 77.99, 100));

        //then
        verify(mockedPriceListener, times(1)).priceUpdate(anyString(), anyDouble());
    }

    @Test
    public void whenListenerIsNotRegistered_NoOneIsNotified() {
        //given

        //when
        StateMachine SUT = new StateMachine();
        SUT.monitorStockPrice(new StockInfo("ISIN", 77.99, 100));

        //then
        verify(mockedPriceListener, never()).priceUpdate(anyString(), anyDouble());
    }

    @Test
    public void whenListenerIsRemoved_eventsAreNotNotifiedToListener() {
        //given

        //when
        StateMachine SUT = new StateMachine();
        SUT.addPriceListener(mockedPriceListener);
        SUT.removePriceListener(mockedPriceListener);
        SUT.monitorStockPrice(new StockInfo("ISIN", 97.99, 101));

        //then
        verify(mockedPriceListener, never()).priceUpdate(anyString(), anyDouble());
    }
}
