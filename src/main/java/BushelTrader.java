import exceptions.InvalidAcreagePurchaseException;

import static java.lang.Float.valueOf;

public class BushelTrader {
  private City city;
  private RandomnessCalculator randomnessCalculator;

  public BushelTrader(City city, RandomnessCalculator randomnessCalculator) {
    this.city = city;
    this.randomnessCalculator = randomnessCalculator;
  }

  public void sellBushels(Float bushelsToSell) throws InvalidAcreagePurchaseException {
    Float bushelsPerAcre = valueOf(randomnessCalculator.calculateRandomnessBetween(17, 26));

    if(isBushelsSoldValidAmount(bushelsToSell, bushelsPerAcre)){
      calculateNewAcreage(bushelsToSell, bushelsPerAcre);
    }
    else{
      throw new InvalidAcreagePurchaseException();
    }
  }

  private boolean isBushelsSoldValidAmount(Float bushelsToSell, Float bushelsPerAcre) {
    return bushelsToSell % bushelsPerAcre == 0;
  }

  private void calculateNewAcreage(Float bushelsToSell, Float bushelsPerAcre) {
    Float acresBought = bushelsToSell/bushelsPerAcre;
    city.setBushelCount(city.getBushelCount() - bushelsToSell.intValue());
    city.setAcreage(city.getAcreage() + acresBought.intValue());
  }

  public void sellLand(Integer acresToSell) {
    Integer bushelsPerAcre = randomnessCalculator.calculateRandomnessBetween(17, 26);

    city.setBushelCount(calculateBushelCount(acresToSell, bushelsPerAcre));
    city.setAcreage(city.getAcreage() - acresToSell);
  }

  private int calculateBushelCount(Integer acresToSell, Integer bushelsPerAcre) {
    int bushelsToAddToCity = bushelsPerAcre * acresToSell;
    return bushelsToAddToCity + city.getBushelCount();
  }
}
