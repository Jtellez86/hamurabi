import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityTest {

  @Mock
  RandomnessCalculator calculator;

  City city;

  @Before
  public void setUp() throws Exception {
    city = new City(1000);
  }

  @Test
  public void shouldInitializeCity() throws Exception {
    when(calculator.calculateRandomnessBetween(0,7)).thenReturn(2);
    when(calculator.calculateRandomnessBetween(1,5)).thenReturn(3);
    when(calculator.calculateRandomnessBetween(0,200)).thenReturn(100);
    when(calculator.calculateRandomnessBetween(17,26)).thenReturn(20);
    city.setRandomnesscalculator(calculator);


    city.startYear();

    assertThat(city.getDeathCount()).isEqualTo(0);
    assertThat(city.getNewCitizens()).isEqualTo(2);
    assertThat(city.getPopulation()).isEqualTo(102);
    assertThat(city.getAcreage()).isEqualTo(1000);
    assertThat(city.getBushelsPerAcre()).isEqualTo(3);
    assertThat(city.getBushelsHarvested()).isEqualTo(3000);
    assertThat(city.getBushelsEatenByRats()).isEqualTo(100);

    //after harvest and rat loss
    assertThat(city.getBushelCount()).isEqualTo(3900);

    assertThat(city.getValueOfLandInBushels()).isEqualTo(20);
  }
}