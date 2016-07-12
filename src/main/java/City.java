public class City {

  private Integer bushelCount;
  private Integer population;
  private Integer acreage;
  private Integer bushelsToSaveForFood;
  private Integer deathCount;
  private Integer newCitizens;
  private Integer bushelsEatenByRats;
  private Integer valueOfLandInBushels;
  private RandomnessCalculator randomnesscalculator;
  private Integer bushelsHarvested;
  private Integer bushelsPerAcre;
  private RoundUpdater updater;

  public City(Integer startingBushels) {
    this.bushelCount = startingBushels;
    this.population = 100;
    this.acreage = 1000;
    this.bushelsToSaveForFood = 0;
    this.deathCount = 0;
    this.newCitizens = 0;
    randomnesscalculator = new RandomnessCalculator();
    updater = new RoundUpdater();
  }

  public void initializeCity() {
    this.setNewCitizens(randomnesscalculator.calculateRandomnessBetween(0, 7));
    updater.addCitizens(this);

    this.setBushelsEatenByRats(randomnesscalculator.calculateRandomnessBetween(0, 200));
    updater.subtractRatLoss(this);

    this.setBushelsPerAcre(randomnesscalculator.calculateRandomnessBetween(1, 5));
    updater.updateBushelCount(this);

    this.setValueOfLandInBushels(randomnesscalculator.calculateRandomnessBetween(17, 26));
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

  public void setPopulation(Integer population) {
    this.population = population;
  }

  public Integer getAcreage() {
    return acreage;
  }

  public void setAcreage(Integer acreage) {
    this.acreage = acreage;
  }

  public Integer getDeathCount() {
    return deathCount;
  }

  public Integer getNewCitizens() {
    return newCitizens;
  }

  public void setNewCitizens(Integer newCitizens) {
    this.newCitizens = newCitizens;
  }

  public void setBushelsEatenByRats(Integer bushelsEatenByRats) {
    this.bushelsEatenByRats = bushelsEatenByRats;
  }

  public void setValueOfLandInBushels(Integer valueOfLandInBushels) {
    this.valueOfLandInBushels = valueOfLandInBushels;
  }

  public Integer getValueOfLandInBushels() {
    return valueOfLandInBushels;
  }

  public Integer getBushelsHarvested() {
    return bushelsHarvested;
  }

  public void setBushelsHarvested(Integer bushelsHarvested) {
    this.bushelsHarvested = bushelsHarvested;
  }

  public Integer getBushelsToSaveForFood() {
    return bushelsToSaveForFood;
  }

  public Integer getBushelsEatenByRats() {
    return bushelsEatenByRats;
  }

  public void setBushelsToSaveForFood(Integer bushelsToSaveForFood) {
    this.bushelsToSaveForFood = bushelsToSaveForFood;
  }

  public Integer getBushelsPerAcre() {
    return this.bushelsPerAcre;
  }

  private void setBushelsPerAcre(Integer bushelsPerAcre) {
    this.bushelsPerAcre = bushelsPerAcre;
  }

  public void setRandomnesscalculator(RandomnessCalculator randomnesscalculator) {
    this.randomnesscalculator = randomnesscalculator;
  }
}
