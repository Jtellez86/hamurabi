public class City {

  private Integer bushelCount = 0;
  private Integer population;

  public City(Integer startingBushels) {
    this.bushelCount = startingBushels;
    this.population = 100;
  }

  public Integer getBushelCount() {
    return bushelCount;
  }

  public void setBushelCount(Integer bushelCount) {
    this.bushelCount = bushelCount;
  }

  public Integer getPopulation() {
    return population;
  }
}
