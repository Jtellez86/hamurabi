import exceptions.InvalidAcreagePurchaseException;

import static java.lang.Float.valueOf;

public class BushelTrader {
  private RandomnessCalculator randomnessCalculator;

  public BushelTrader(RandomnessCalculator randomnessCalculator) {
    this.randomnessCalculator = randomnessCalculator;
  }

  public void useBushelsToBuyLand(Float bushelsToSell, City city) throws InvalidAcreagePurchaseException {
    Float bushelsPerAcre = valueOf(randomnessCalculator.calculateRandomnessBetween(17, 26));

    if(isBushelsSoldValidAmount(bushelsToSell, bushelsPerAcre)){
      calculateNewAcreage(bushelsToSell, bushelsPerAcre, city);
    }
    else{
      throw new InvalidAcreagePurchaseException();
    }
  }

  public void useLandToBuyBushels(Integer bushels, City city) {
    Integer bushelsPerAcre = randomnessCalculator.calculateRandomnessBetween(17, 26);

    city.setBushelCount(city.getBushelCount() + bushels);
    city.setAcreage(city.getAcreage() - calculateAcres(bushels, bushelsPerAcre));
  }

  private int calculateAcres(Integer bushelsToSell, Integer bushelsPerAcre) {
    return bushelsToSell/bushelsPerAcre;
  }

  private boolean isBushelsSoldValidAmount(Float bushelsToSell, Float bushelsPerAcre) {
    return bushelsToSell % bushelsPerAcre == 0;
  }

  private void calculateNewAcreage(Float bushelsToSell, Float bushelsPerAcre, City city) {
    Float acresBought = bushelsToSell/bushelsPerAcre;
    city.setBushelCount(city.getBushelCount() - bushelsToSell.intValue());
    city.setAcreage(city.getAcreage() + acresBought.intValue());
  }
}
