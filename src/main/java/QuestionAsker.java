import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import static java.lang.Integer.parseInt;


public class QuestionAsker {
  public static final String FOOD_QUESTION = "How many bushels do you wish to feed your people?";
  public static final String PLANT_QUESTION = "How many acres do you wish to plant with seed?";
  public static final String BUSHEL_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d bushels of grain. Now then, please give me a number.%n";
  public static final String ACREAGE_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d acres of land. Now then, please give me a number.%n";
  public static final String LAND_QUESTION = "How many acres do you wish to buy or sell?(enter a negative amount to sell acres for bushels)";

  private InputStream inputStream;
  private BufferedReader bufferedReader;

  private final PrintStream output;
  private BushelTrader trader;

  QuestionAsker(InputStream in, PrintStream out, RandomnessCalculator calculator) {
    this.inputStream = in;
    this.bufferedReader = new BufferedReader(new InputStreamReader(in));
    this.output = out;
    this.trader = new BushelTrader(calculator);
  }

  QuestionAsker(BufferedReader bufferedReader, PrintStream out, RandomnessCalculator calculator) {
    this.bufferedReader = bufferedReader;
    this.output = out;
    this.trader = new BushelTrader(calculator);
  }

  public void askHowMuchToUseForFood(City city) throws IOException {
    boolean questionAnswered = false;
    output.println(FOOD_QUESTION);
    while(!questionAnswered) {
      String stringInput = bufferedReader.readLine();

      while (!isInteger(stringInput)) {
        printCorrectionMessage(city);
        stringInput = bufferedReader.readLine();
      }
      Integer bushelsToUseAsFood = Integer.valueOf(stringInput);
      if (doesCityHaveTheResources(bushelsToUseAsFood, city.getBushelCount())) {
        city.setBushelsToSaveForFood(bushelsToUseAsFood);
        city.setBushelCount(city.getBushelCount() - bushelsToUseAsFood);
        questionAnswered = true;
      }
      else{
        printCorrectionMessage(city);
      }
    }
  }

  private void printCorrectionMessage(City city) {
    output.printf(BUSHEL_CORRECTION_MESSAGE, city.getBushelCount());
    output.println(FOOD_QUESTION);
  }


  public void askHowMuchToPlant(City city) throws IOException {
    output.println(PLANT_QUESTION);
    Integer bushelsToPlant = inputStream.read();
    while (!doesCityHaveTheResources(bushelsToPlant, city.getBushelCount())) {
      output.printf(BUSHEL_CORRECTION_MESSAGE, city.getBushelCount());
      output.println(PLANT_QUESTION);
      bushelsToPlant = inputStream.read();
    }
    city.setBushelCount(city.getBushelCount() - bushelsToPlant);
  }

  public void askHowMuchLandToTrade(City city) throws IOException {
    output.println(LAND_QUESTION);
    Integer acresToTrade = inputStream.read();

    while (!doesCityHaveTheResources(Math.abs(acresToTrade), city.getAcreage())) {
      output.printf(ACREAGE_CORRECTION_MESSAGE, city.getAcreage());
      output.println(LAND_QUESTION);
      acresToTrade = inputStream.read();
    }
    if (acresToTrade < 0) {
      trader.sellAcreage(Math.abs(acresToTrade), city);
    } else {
      trader.buyAcreage(acresToTrade, city);
    }
  }

  public boolean isInteger(String invalidInput) {
    boolean isValid = true;
    try {
      parseInt(invalidInput);
    } catch (NumberFormatException e) {
      isValid = false;
    }
    return isValid;

  }

  public boolean doesCityHaveTheResources(Integer bushelsRequested, Integer bushelsAvailable) {
    return bushelsRequested <= bushelsAvailable;
  }
}
