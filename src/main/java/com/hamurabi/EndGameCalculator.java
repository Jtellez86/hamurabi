package com.hamurabi;

import com.hamurabi.exceptions.TerribleRulerException;

public class EndGameCalculator {



  public boolean isGameOver(City city) throws TerribleRulerException {
    boolean isOver = false;

    Integer adjustedPopulation = city.getPopulation() - city.getDeathCount();
    Float fiftyFivePercentOfPop = (city.getPopulation() * .55f);

    if(isCityPopulationWipedOut(city) || is45PercentDead(adjustedPopulation, fiftyFivePercentOfPop)){
      isOver = true;
      throw new TerribleRulerException();
    }

    return isOver;
  }

  private boolean is45PercentDead(Integer adjustedPopulation, Float fiftyFivePercentOfPop) {
    return adjustedPopulation <= fiftyFivePercentOfPop.intValue();
  }

  private boolean isCityPopulationWipedOut(City city) {
    return city.getPopulation() <= 0;
  }
}
