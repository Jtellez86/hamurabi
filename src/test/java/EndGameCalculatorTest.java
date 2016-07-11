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

    assertThat(calculator.isGameOver(city, 100)).isTrue();
  }

  @Test(expected = TerribleRulerException.class)
  public void shouldLoseIfPopulationIs45PercentIsLost() throws TerribleRulerException {
    City city = new City(10);

    assertThat(calculator.isGameOver(city, 45)).isTrue();
  }

  @Test
  public void shouldContinueIfPopulationHasNotLost45PercentOfPopulation() throws TerribleRulerException {
    City city = new City(10);

    assertThat(calculator.isGameOver(city, 44)).isFalse();
  }
}