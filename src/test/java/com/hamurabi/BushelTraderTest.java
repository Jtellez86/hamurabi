package com.hamurabi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BushelTraderTest {

  @Mock
  private PrintStream mockedPrintStream;

  private BushelTrader trader;
  private City city;

  @Before
  public void setUp() throws Exception {
    city = new City(20);
    city.setValueOfLandInBushels(20);
    trader = new BushelTrader();
  }

  @Test
  public void shouldTradeBushelsForAcreage(){
    trader.buyAcreage(1, city);

    assertThat(city.getAcreage()).isEqualTo(1001);
    assertThat(city.getBushelCount()).isEqualTo(0);
    verify(mockedPrintStream, times(0)).println(anyString());
  }

  @Test
  public void shouldTradeAcreageForBushels(){
    trader.sellAcreage(1, city);

    assertThat(city.getAcreage()).isEqualTo(999);
    assertThat(city.getBushelCount()).isEqualTo(40);
    verify(mockedPrintStream, times(0)).println(anyString());
  }

  @Test
  public void shouldNotAllowNegativeBushels(){
    trader.sellAcreage(1, city);

    assertThat(city.getAcreage()).isEqualTo(999);
    assertThat(city.getBushelCount()).isEqualTo(40);
    verify(mockedPrintStream, times(0)).println(anyString());
  }
}