package calculators;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RatLossCalculatorTest {

  @Test
  public void shouldReturnValueBetween0and100(){

    RatLossCalculator calculator = new RatLossCalculator();

    int lostBushels = calculator.calculateLoss();

    assertThat(lostBushels).isBetween(0, 100);

  }
}