package com.hamurabi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static java.lang.Math.abs;


public class Advisor {
  public static final String FOOD_QUESTION = "How many bushels do you wish to feed your people?";
  public static final String PLANT_QUESTION = "How many acres do you wish to plant with seed? (1 bushel per acre)";
  public static final String LAND_QUESTION = "How many acres do you wish to buy or sell?(enter a negative amount to sell acres for bushels)";
  public static final String BUSHEL_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d bushels of grain. Now then, please give me a number.\n";
  public static final String ACREAGE_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d acres of land. Now then, please give me a number.\n";
  public static final String FARMER_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d citizens that can farm 3 acres each. Now then, please give me a number.\n";
  public static final int ACRES_PER_CITIZEN = 3;

  private BufferedReader bufferedReader;

  private final PrintStream output;
  private BushelTrader trader;

  public Advisor(BufferedReader bufferedReader, PrintStream out) {
    this.bufferedReader = bufferedReader;
    this.output = out;
    this.trader = new BushelTrader();
  }

  public void askHowMuchToUseForFood(City city) throws IOException {
    boolean questionAnswered = false;
    output.println(FOOD_QUESTION);
    while (!questionAnswered) {
      String stringInput = bufferedReader.readLine();

      while (!isAnInteger(stringInput) || (valueOf(stringInput) < 0)) {
        printCorrectionMessage(BUSHEL_CORRECTION_MESSAGE, FOOD_QUESTION, city.getBushelCount());
        stringInput = bufferedReader.readLine();
      }
      Integer bushelsToUseAsFood = valueOf(stringInput);

      if (doesCityHaveTheResources(bushelsToUseAsFood, city.getBushelCount(), BUSHEL_CORRECTION_MESSAGE, FOOD_QUESTION)) {
        city.setBushelsToUseForFood(bushelsToUseAsFood);
        city.setBushelCount(city.getBushelCount() - bushelsToUseAsFood);
        questionAnswered = true;
      }
    }
  }

  public void askHowManyBushelsToPlant(City city) throws IOException {
    boolean questionAnswered = false;
    output.println(PLANT_QUESTION);
    while (!questionAnswered) {
      String stringInput = bufferedReader.readLine();

      while (!isAnInteger(stringInput)) {
        printCorrectionMessage(BUSHEL_CORRECTION_MESSAGE, PLANT_QUESTION, city.getBushelCount());
        stringInput = bufferedReader.readLine();
      }
      Integer landToFarm = valueOf(stringInput);
      if (doesCityHaveTheResources(landToFarm, city.getBushelCount(), BUSHEL_CORRECTION_MESSAGE, PLANT_QUESTION)
          && doesCityHaveEnoughFarmers(city.getPopulation(), landToFarm, FARMER_CORRECTION_MESSAGE, PLANT_QUESTION)
          && doesCityHaveTheResources(landToFarm, city.getAcreage(), ACREAGE_CORRECTION_MESSAGE, PLANT_QUESTION)) {

        city.setBushelsToUseForPlanting(landToFarm);
        city.setBushelCount(city.getBushelCount() - landToFarm);
        city.setAcreageToFarm(landToFarm);
        questionAnswered = true;
      }
    }
  }

  private boolean doesCityHaveEnoughFarmers(Integer population, Integer bushelsToUseForPlanting, String correctionMessage, String question) {
    Integer acresPopulationCanSupport = population * ACRES_PER_CITIZEN;
    boolean isThereEnough = acresPopulationCanSupport >= bushelsToUseForPlanting;

    if (!isThereEnough) {
      printCorrectionMessage(correctionMessage, question, population);
    }
    return isThereEnough;
  }

  public void askHowMuchLandToTrade(City city) throws IOException {
    boolean questionAnswered = false;
    output.println(LAND_QUESTION);
    while (!questionAnswered) {
      String stringInput = bufferedReader.readLine();

      while (!isAnInteger(stringInput)) {
        printCorrectionMessage(ACREAGE_CORRECTION_MESSAGE, LAND_QUESTION, city.getAcreage());
        stringInput = bufferedReader.readLine();
      }
      Integer acresToTrade = valueOf(stringInput);

      if(acresToTrade.equals(0)){
        city.setAcresToTrade(acresToTrade);
        questionAnswered = true;
      }
      else if (isThereEnoughLandToSell(city, acresToTrade)) {
        trader.sellAcreage(abs(acresToTrade), city);
        questionAnswered = true;
      }
      else if (isThereEnoughBushelsToBuyLand(city, acresToTrade)) {
        trader.buyAcreage(acresToTrade, city);
        questionAnswered = true;
      }
    }
  }

  private boolean isThereEnoughBushelsToBuyLand(City city, Integer acresToTrade) {
    return acresToTrade > 0 && doesCityHaveTheResources(calculateBushelsForPurchaseOfAcres(city.getValueOfLandInBushels(), acresToTrade), city.getBushelCount(), BUSHEL_CORRECTION_MESSAGE, LAND_QUESTION);
  }

  private boolean isThereEnoughLandToSell(City city, Integer acresToTrade) {
    return acresToTrade < 0 && doesCityHaveTheResources(abs(acresToTrade), ( city.getAcreage() - city.getAcreageToFarm()), ACREAGE_CORRECTION_MESSAGE, LAND_QUESTION);
  }

  private void printCorrectionMessage(String correctionMessage, String question, Integer actualAmount) {
    output.printf(correctionMessage, actualAmount);
    output.println(question);
  }

  public boolean isAnInteger(String invalidInput) {
    boolean isValid = true;
    try {
      parseInt(invalidInput);
    } catch (NumberFormatException e) {
      isValid = false;
    }
    return isValid;

  }

  private boolean doesCityHaveTheResources(Integer requested, Integer available, String correction, String question) {
    boolean isThereEnough = available >= requested;

    if (!isThereEnough) {
      printCorrectionMessage(correction, question, available);
    }

    return isThereEnough;
  }

  public Integer calculateBushelsForPurchaseOfAcres(Integer bushelPerAcre, Integer acresToPurchase) {
    return (bushelPerAcre * acresToPurchase);
  }

  public void informOfPlague() {
    output.println("A plague has reduced your population by half! What shall we do!?");
  }

  public void impeach() {
    output.println("Due to this extreme mismanagement, you have not only been impeached and thrown out of office, but you have also been declared 'National Fink'!");
  }

  public void giveYearlyUpdate(City city, Integer yearsRuled) {
    output.println("\nHammurabi: I beg to report to you,\n");
    output.printf("In Year %d, %d people starved.\n", yearsRuled, city.getDeathCount());
    output.printf("%d people came to the city.\n", city.getNewCitizens());
    output.printf("The city population is now %d.\n", city.getPopulation());
    output.printf("The city now owns %d acres.\n", city.getAcreage());
    output.printf("You harvested %d bushels per acre\n", city.getBushelsHarvestedPerAcre());
    output.printf("Rats ate %d bushels\n", city.getBushelsEatenByRats());
    output.printf("You now have %d bushels in store\n", city.getBushelCount());
    output.printf("Land is trading at %d bushels per acre\n", city.getValueOfLandInBushels());
  }
}
