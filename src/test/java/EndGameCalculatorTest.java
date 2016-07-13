import exceptions.TerribleRulerException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EndGameCalculatorTest {

  EndGameCalculator calculator;
  @Before
  public void setUp() throws Exception {
    calculator = new EndGameCalculator();

  }

  @Test(expected = TerribleRulerException.class)
  public void shouldLoseIfPopulationIsWipedOut() throws TerribleRulerException {
    City city = new City(10);
    city.setDeathCount(100);

    assertThat(calculator.isGameOver(city)).isTrue();
  }

  @Test(expected = TerribleRulerException.class)
  public void shouldLoseIfPopulationIs45PercentIsLost() throws TerribleRulerException {
    City city = new City(20);
    city.setDeathCount(55);

    assertThat(calculator.isGameOver(city)).isTrue();
  }

  @Test
  public void shouldContinueIfPopulationHasNotLost45PercentOfPopulation() throws TerribleRulerException {
    City city = new City(10);
    city.setDeathCount(40);

    assertThat(calculator.isGameOver(city)).isFalse();
  }
}