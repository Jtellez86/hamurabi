package com.hamurabi;

public class EndGameCalculator {



  public boolean isGameOver(City city){
    boolean isOver = false;

    Integer adjustedPopulation = city.getPopulation() - city.getDeathCount();
    Float fiftyFivePercentOfPop = (city.getPopulation() * .55f);

    if(isCityPopulationWipedOut(city) || is45PercentDead(adjustedPopulation, fiftyFivePercentOfPop)){
      isOver = true;
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
