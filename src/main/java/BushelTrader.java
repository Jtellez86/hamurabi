public class BushelTrader {
  private RandomnessCalculator randomnessCalculator;

  public BushelTrader(RandomnessCalculator randomnessCalculator) {
    this.randomnessCalculator = randomnessCalculator;
  }

  public void sellAcreage(Integer acresToSell, City city) {
    Integer bushelsPerAcre = randomnessCalculator.calculateRandomnessBetween(17, 26);

    deductAcreageFromCity(Math.abs(acresToSell), bushelsPerAcre, city);
  }

  public void buyAcreage(Integer acreageToAcquire, City city) {
    Integer bushelsPerAcre = randomnessCalculator.calculateRandomnessBetween(17, 26);

    city.setBushelCount(city.getBushelCount() - (acreageToAcquire * bushelsPerAcre));
    city.setAcreage(city.getAcreage() + acreageToAcquire);
  }

  private void deductAcreageFromCity(Integer acresToSell, Integer bushelsPerAcre, City city) {
    city.setAcreage(city.getAcreage() - acresToSell);
    city.setBushelCount(city.getBushelCount() + (acresToSell * bushelsPerAcre));
  }
}
