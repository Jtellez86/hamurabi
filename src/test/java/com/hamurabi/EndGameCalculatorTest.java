package com.hamurabi;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EndGameCalculatorTest {

  private EndGameCalculator calculator;
  private City city;

  @Before
  public void setUp() throws Exception {
    calculator = new EndGameCalculator();
    city = new City(10);
    city.setPopulation(100);
    city.setDeathCount(100);
  }

  @Test
  public void shouldLoseIfPopulationIsWipedOut(){
    assertThat(calculator.isGameOver(city)).isTrue();
  }

  @Test
  public void shouldLoseIfPopulationIs45PercentIsLost(){
    city.setDeathCount(45);

    assertThat(calculator.isGameOver(city)).isTrue();
  }

  @Test
  public void shouldContinueIfPopulationHasNotLost45PercentOfPopulation(){
    city.setDeathCount(44);

    assertThat(calculator.isGameOver(city)).isFalse();
  }
}