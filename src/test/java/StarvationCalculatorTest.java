import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StarvationCalculatorTest {

  private StarvationCalculator calculator;

  @Before
  public void setUp() throws Exception {

    calculator = new StarvationCalculator();
  }

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