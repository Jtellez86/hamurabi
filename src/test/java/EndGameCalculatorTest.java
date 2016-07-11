import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EndGameCalculatorTest {

  EndGameCalculator calculator;
  @Before
  public void setUp() throws Exception {
    calculator = new EndGameCalculator();

  }

  @Test
  public void shouldLoseIfPopulationIsWipedOut() throws Exception {
    City city = new City(10);

    assertThat(calculator.isGameOver(city, 100)).isTrue();
  }

  @Test
  public void shouldLoseIfPopulationIs45PercentIsLost() throws Exception {
    City city = new City(10);

    assertThat(calculator.isGameOver(city, 45)).isTrue();
  }

  @Test
  public void shouldContinueIfPopulationHasNotLost45PercentOfPopulation() throws Exception {
    City city = new City(10);

    assertThat(calculator.isGameOver(city, 44)).isFalse();
  }
}