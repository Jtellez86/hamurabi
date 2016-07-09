package calculators;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class StarvationCalculatorTest {

  private StarvationCalculator calculator = new StarvationCalculator();

  @Test
  public void shouldReturnZeroDeathsFromStarvation() {

    Integer diedOfStarvation  = calculator.calculateDeaths(100, 2000);

    assertThat(diedOfStarvation).isEqualTo(0);

  }

  @Test
  public void shouldReturn10DeathsFromStarvation() {
    Integer diedOfStarvation  = calculator.calculateDeaths(20, 200);

    assertThat(diedOfStarvation).isEqualTo(10);
  }

  @Test
  public void shouldReturn0WithExcessRations() {
    Integer diedOfStarvation  = calculator.calculateDeaths(1, 30);

    assertThat(diedOfStarvation).isEqualTo(0);
  }
}