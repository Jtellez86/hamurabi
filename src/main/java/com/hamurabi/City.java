package com.hamurabi;

public class City {

  private Integer bushelCount;
  private Integer population;
  private Integer acreage;
  private Integer bushelsToUseForFood;
  private Integer bushelsToUseForPlanting;
  private Integer deathCount;
  private Integer newCitizens;
  private Integer bushelsEatenByRats;
  private Integer valueOfLandInBushels;
  private Integer totalBushelsHarvested;
  private Integer bushelsHarvestedPerAcre;
  private Integer acresToTrade;
  private RoundUpdater updater;
  private RandomnessCalculator randomnesscalculator;
  private StarvationCalculator starvationCalculator;

  public City(Integer startingBushels) {
    this.bushelCount = startingBushels;
    this.population = 100;
    this.acreage = 1000;
    this.bushelsToUseForFood = 0;
    this.bushelsToUseForPlanting = 0;
    this.deathCount = 0;
    this.newCitizens = 0;
    this.acresToTrade = 0;
    this.updater = new RoundUpdater();
    this.randomnesscalculator = new RandomnessCalculator();
    this.starvationCalculator = new StarvationCalculator();
  }

  public void startYear() {
    this.setNewCitizens(randomnesscalculator.calculateRandomnessBetween(0, 7));
    updater.addCitizens(this);

    this.setBushelsEatenByRats(randomnesscalculator.calculateRandomnessBetween(0, 200));
    updater.subtractRatLoss(this);

    this.setBushelsHarvestedPerAcre(randomnesscalculator.calculateRandomnessBetween(1, 5));
    updater.updateBushelCountWithHarvest(this);

    this.setValueOfLandInBushels(randomnesscalculator.calculateRandomnessBetween(17, 26));
  }

  public void endYear() {
    Integer deathCount = starvationCalculator.calculateDeaths(this.getPopulation(), this.getBushelsToUseForFood());
    this.setDeathCount(deathCount);
    this.setPopulation(this.getPopulation() - deathCount);
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

  public Integer getTotalBushelsHarvested() {
    return totalBushelsHarvested;
  }

  public void setTotalBushelsHarvested(Integer totalBushelsHarvested) {
    this.totalBushelsHarvested = totalBushelsHarvested;
  }

  public Integer getBushelsToUseForFood() {
    return bushelsToUseForFood;
  }

  public Integer getBushelsEatenByRats() {
    return bushelsEatenByRats;
  }

  public void setBushelsToUseForFood(Integer bushelsToSaveForFood) {
    this.bushelsToUseForFood = bushelsToSaveForFood;
  }

  public Integer getBushelsHarvestedPerAcre() {
    return this.bushelsHarvestedPerAcre;
  }

  public void setBushelsHarvestedPerAcre(Integer bushelsHarvestedPerAcre) {
    this.bushelsHarvestedPerAcre = bushelsHarvestedPerAcre;
  }

  public void setRandomnesscalculator(RandomnessCalculator randomnesscalculator) {
    this.randomnesscalculator = randomnesscalculator;
  }

  public Integer getBushelsToUseForPlanting() {
    return bushelsToUseForPlanting;
  }

  public void setBushelsToUseForPlanting(Integer bushelsToSaveForPlanting) {
    this.bushelsToUseForPlanting = bushelsToSaveForPlanting;
  }

  public void setAcresToTrade(Integer acresToTrade) {
    this.acresToTrade = acresToTrade;
  }

  public Integer getAcresToTrade() {
    return acresToTrade;
  }

  public void setDeathCount(Integer deathCount) {
    this.deathCount = deathCount;
  }
}
