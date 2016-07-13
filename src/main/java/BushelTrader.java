public class BushelTrader {

  public void sellAcreage(Integer acresToSell, City city) {
    city.setAcresToTrade(acresToSell);
    city.setAcreage(city.getAcreage() - acresToSell);
    city.setBushelCount(city.getBushelCount() + (acresToSell * city.getValueOfLandInBushels()));
  }

  public void buyAcreage(Integer acresToBuy, City city) {
    city.setAcresToTrade(acresToBuy);
    city.setAcreage(city.getAcreage() + acresToBuy);
    city.setBushelCount(city.getBushelCount() - (acresToBuy * city.getValueOfLandInBushels()));
  }

}
