package com.hamurabi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static java.lang.Math.abs;


public class QuestionAsker {
  public static final String FOOD_QUESTION = "How many bushels do you wish to feed your people?";
  public static final String PLANT_QUESTION = "How many acres do you wish to plant with seed? (1 bushel per acre)";
  public static final String BUSHEL_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d bushels of grain. Now then, please give me a number.%n";
  public static final String ACREAGE_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d acres of land. Now then, please give me a number.%n";
  public static final String LAND_QUESTION = "How many acres do you wish to buy or sell?(enter a negative amount to sell acres for bushels)";

  private BufferedReader bufferedReader;

  private final PrintStream output;
  private BushelTrader trader;

  public QuestionAsker(BufferedReader bufferedReader, PrintStream out) {
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
      Integer bushelsToUseForPlanting = valueOf(stringInput);
      if (doesCityHaveTheResources(bushelsToUseForPlanting, city.getBushelCount(), BUSHEL_CORRECTION_MESSAGE, PLANT_QUESTION)) {
        city.setBushelsToUseForPlanting(bushelsToUseForPlanting);
        city.setBushelCount(city.getBushelCount() - bushelsToUseForPlanting);
        questionAnswered = true;
      }
    }
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
    return acresToTrade < 0 && doesCityHaveTheResources(abs(acresToTrade), city.getAcreage(), ACREAGE_CORRECTION_MESSAGE, LAND_QUESTION);
  }

  private void printCorrectionMessage(String correctionMessage, String question, Integer count) {
    output.printf(correctionMessage, count);
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
}
