import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BushelTraderTest {

  @Mock
  RandomnessCalculator mockedRandomnessCalculator;

  @Mock
  PrintStream mockedPrintStream;

  private BushelTrader trader;
  private City city;

  @Test
  public void shouldTradeBushelsForAcreage(){
    when(mockedRandomnessCalculator.calculateRandomnessBetween(17, 26)).thenReturn(20);
    city = new City(20);
    trader = new BushelTrader(mockedRandomnessCalculator);

    trader.buyAcreage(1, city);

    assertThat(city.getAcreage()).isEqualTo(1001);
    assertThat(city.getBushelCount()).isEqualTo(0);
    verify(mockedPrintStream, times(0)).println(anyString());
  }

  @Test
  public void shouldTradeAcreageForBushels(){
    when(mockedRandomnessCalculator.calculateRandomnessBetween(17, 26)).thenReturn(20);
    city = new City(20);
    trader = new BushelTrader(mockedRandomnessCalculator);

    trader.sellAcreage(-1, city);

    assertThat(city.getAcreage()).isEqualTo(999);
    assertThat(city.getBushelCount()).isEqualTo(40);
    verify(mockedPrintStream, times(0)).println(anyString());
  }
}