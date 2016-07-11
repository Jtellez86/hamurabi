package calculators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StarvationCalculatorTest {

  @Mock
  PrintStream mockedPrintStream;

  private StarvationCalculator calculator;



  @Before
  public void setUp() throws Exception {

    calculator = new StarvationCalculator(mockedPrintStream);
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
    verify(mockedPrintStream).printf("You starved %d people in one year!!!%n", 10);
  }

  @Test
  public void shouldReturn0WithExcessRations() {
    Integer diedOfStarvation  = calculator.calculateDeaths(1, 30);

    assertThat(diedOfStarvation).isEqualTo(0);
  }
}