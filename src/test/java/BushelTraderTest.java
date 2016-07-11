import exceptions.InvalidAcreagePurchaseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BushelTraderTest {

  @Mock
  RandomnessCalculator randomnessCalculator;

  @Mock
  PrintStream mockedPrintStream;

  private BushelTrader trader;
  private City city;

  @Test
  public void shouldAddBushelsFromSellingLandToBushelSurplus() throws InvalidAcreagePurchaseException {
    when(randomnessCalculator.calculateRandomnessBetween(17, 26)).thenReturn(20);
    city = new City(10);
    trader = new BushelTrader(city, randomnessCalculator);

    trader.sellLand(5);

    assertThat(city.getAcreage()).isEqualTo(995);
    assertThat(city.getBushelCount()).isEqualTo(110);
    verify(mockedPrintStream, times(0)).println(anyString());
  }

  @Test
  public void shouldRemoveBushelsFromSurplusWhenBuyingLand() throws InvalidAcreagePurchaseException {
    when(randomnessCalculator.calculateRandomnessBetween(17, 26)).thenReturn(20);

    city = new City(20);
    trader = new BushelTrader(city, randomnessCalculator);
    trader.sellBushels(20f);

    assertThat(city.getAcreage()).isEqualTo(1001);
    assertThat(city.getBushelCount()).isEqualTo(0);
    verify(mockedPrintStream, times(0)).println(anyString());
  }

  @Test(expected = InvalidAcreagePurchaseException.class)
  public void shouldNotRemoveBushelsFromSurplusWhenBuyingLandWithLessThanCostOfAcre() throws InvalidAcreagePurchaseException {
    when(randomnessCalculator.calculateRandomnessBetween(17, 26)).thenReturn(20);

    city = new City(20);
    trader = new BushelTrader(city, randomnessCalculator);
    trader.sellBushels(19f);

    assertThat(city.getAcreage()).isEqualTo(1000);
    assertThat(city.getBushelCount()).isEqualTo(20);
  }
}