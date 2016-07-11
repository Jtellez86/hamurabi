public class City {

  private Integer bushelCount = 0;
  private Integer population;
  private Integer acreage;

  public City(Integer startingBushels) {
    this.bushelCount = startingBushels;
    this.population = 100;
    this.acreage = 1000;
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

  public Integer getAcreage() {
    return acreage;
  }

  public void setAcreage(Integer acreage) {
    this.acreage = acreage;
  }
}
