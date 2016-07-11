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
  public void shouldDetermineYouAreUnfitRulerIfPopulationIsWipedOut() throws Exception {
    City city = new City(10);

    assertThat(calculator.isGameOver(city, 100)).isTrue();
  }

  @Test
  public void shouldDetermineYouAreUnfitRulerIfPopulationIs45PercentIsLost() throws Exception {
    City city = new City(10);

    assertThat(calculator.isGameOver(city, 45)).isTrue();
  }
}