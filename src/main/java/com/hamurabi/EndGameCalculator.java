package com.hamurabi;

public class EndGameCalculator {

  public boolean isGameOver(City city){
    boolean isOver = false;

    Integer populationAfterDeaths = city.getPopulation() - city.getDeathCount();

    if(isCityPopulationWipedOut(city) || is45PercentDead(populationAfterDeaths, city.getPopulation())){
      isOver = true;
    }

    city.setPopulation(populationAfterDeaths);
    return isOver;
  }

  private boolean is45PercentDead(Integer populationAfterDeaths, Integer population) {
    Float fiftyFivePercentOfPop = (population * .55f);
    return populationAfterDeaths <= fiftyFivePercentOfPop.intValue();
  }

  private boolean isCityPopulationWipedOut(City city) {
    return city.getPopulation() <= 0;
  }
}
