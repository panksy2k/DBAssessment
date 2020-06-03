package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.StateMachine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TradingStrategyTest {

    @Mock
    private StateMachine mockedStateMachine;

    @Mock
    private ExecutionService mockedExecutionService;


    @Test
    public void whenPriceUpdateAndRuleMet_executeOrder_returnOrdersCount() {
        //given

        //when
        TradingStrategy SUT = new TradingStrategy(mockedStateMachine, mockedExecutionService);
        SUT.priceUpdate(PriceListener.getIBMSecurity(), 23.22D);
        SUT.priceUpdate(PriceListener.getIBMSecurity(), 83.22D);

        //then
        assertEquals(1, SUT.getBuyCount().intValue());
    }

    @Test
    public void whenPriceUpdateAndNoRuleMet_skipOrderExecution_returnTrueStats() {
        //given

        //when
        TradingStrategy SUT = new TradingStrategy(mockedStateMachine, mockedExecutionService);
        SUT.priceUpdate(PriceListener.getIBMSecurity(), 23.22D);
        SUT.priceUpdate("APL", 83.22D);
        SUT.priceUpdate(PriceListener.getIBMSecurity(), 54.99);
        SUT.priceUpdate(PriceListener.getIBMSecurity(), 55.0);

        //then
        assertEquals(2, SUT.getBuyCount().intValue());
        assertEquals(4, SUT.getRecordCount().intValue());
    }
}
