package com.hamurabi;

public class RoundUpdater {

  public void addCitizens(City city){
    city.setPopulation(city.getPopulation() + city.getNewCitizens());
  }

  public void subtractRatLoss(City city) {
    city.setBushelCount(city.getBushelCount() - city.getBushelsEatenByRats());
  }

  public void updateBushelCountWithHarvest(City city) {
    Integer bushelsHarvested = city.getBushelsToUseForPlanting() * city.getBushelsHarvestedPerAcre();
    city.setTotalBushelsHarvested(bushelsHarvested);
    city.setBushelCount(city.getBushelCount() + bushelsHarvested);
  }
}
