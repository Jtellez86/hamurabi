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
  RandomnessCalculator mockedRandomnessCalculator;

  @Mock
  PrintStream mockedPrintStream;

  private BushelTrader trader;
  private City city;

  @Test
  public void shouldAddBushelsFromSellingLandToBushelSurplus() throws InvalidAcreagePurchaseException {
    when(mockedRandomnessCalculator.calculateRandomnessBetween(17, 26)).thenReturn(20);
    city = new City(10);
    trader = new BushelTrader(mockedRandomnessCalculator);

    trader.useLandToBuyBushels(20, city);

    assertThat(city.getAcreage()).isEqualTo(999);
    assertThat(city.getBushelCount()).isEqualTo(30);
    verify(mockedPrintStream, times(0)).println(anyString());
  }

  @Test
  public void shouldRemoveBushelsFromSurplusWhenBuyingLand() throws InvalidAcreagePurchaseException {
    when(mockedRandomnessCalculator.calculateRandomnessBetween(17, 26)).thenReturn(20);

    city = new City(20);
    trader = new BushelTrader(mockedRandomnessCalculator);
    trader.useBushelsToBuyLand(20f, city);

    assertThat(city.getAcreage()).isEqualTo(1001);
    assertThat(city.getBushelCount()).isEqualTo(0);
    verify(mockedPrintStream, times(0)).println(anyString());
  }

  @Test(expected = InvalidAcreagePurchaseException.class)
  public void shouldNotRemoveBushelsFromSurplusWhenBuyingLandWithLessThanCostOfAcre() throws InvalidAcreagePurchaseException {
    when(mockedRandomnessCalculator.calculateRandomnessBetween(17, 26)).thenReturn(20);

    city = new City(20);
    trader = new BushelTrader(mockedRandomnessCalculator);
    trader.useBushelsToBuyLand(19f, city);

    assertThat(city.getAcreage()).isEqualTo(1000);
    assertThat(city.getBushelCount()).isEqualTo(20);
  }
}