public class RoundUpdater {

  public void addCitizens(City city){
    city.setPopulation(city.getPopulation() + city.getNewCitizens());
  }

  public void subtractRatLoss(City city) {
    city.setBushelCount(city.getBushelCount() - city.getBushelsEatenByRats());
  }

  public void updateBushelCount(City city) {

    Integer bushelsHarvested = city.getAcreage() * city.getBushelsPerAcre();
    city.setBushelsHarvested(bushelsHarvested);
    city.setBushelCount(city.getBushelCount() + bushelsHarvested);
  }
}