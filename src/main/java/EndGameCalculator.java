public class EndGameCalculator {



  public boolean isGameOver(City city, Integer numberOfDeaths) {
    boolean isOver = false;

    Integer adjustedPopulation = city.getPopulation() - numberOfDeaths;
    Float fiftyFivePercentOfPop = (city.getPopulation() * .55f);

    if(isCityPopulationWipedOut(city)){
      isOver = true;
    }
    else if(adjustedPopulation <= fiftyFivePercentOfPop.intValue()){
      isOver = true;
    }

    return isOver;
  }

  private boolean isCityPopulationWipedOut(City city) {
    return city.getPopulation() <= 0;
  }
}
