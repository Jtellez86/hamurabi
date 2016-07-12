import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static java.lang.Integer.parseInt;


public class QuestionAsker {
  public static final String FOOD_QUESTION = "How many bushels do you wish to feed your people?";
  public static final String PLANT_QUESTION = "How many acres do you wish to plant with seed?";
  public static final String BUSHEL_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d bushels of grain. Now then, please give me a number.%n";
  public static final String ACREAGE_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d acres of land. Now then, please give me a number.%n";
  public static final String LAND_QUESTION = "How many acres do you wish to buy or sell?(enter a negative amount to sell acres for bushels)";
  private final InputStream inputStream;
  private final PrintStream output;
  private BushelTrader trader;

  QuestionAsker(InputStream in, PrintStream out, RandomnessCalculator calculator) {
    this.inputStream = in;
    this.output = out;
    this.trader = new BushelTrader(calculator);
  }


  public void askHowMuchToUseForFood(City city) throws IOException {
    output.println(FOOD_QUESTION);
    Integer bushelsToEat = inputStream.read();
    while (!this.doesCityHaveTheResources(bushelsToEat, city.getBushelCount())) {
      output.printf(BUSHEL_CORRECTION_MESSAGE, city.getBushelCount());
      output.println(FOOD_QUESTION);
      bushelsToEat = inputStream.read();
    }
    city.setBushelsToSaveForFood(bushelsToEat);
    city.setBushelCount(city.getBushelCount() - bushelsToEat);
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
    boolean result = true;
    try {
      parseInt(invalidInput);
    }catch (NumberFormatException e){
      return false;
    }
    return result;

  }

  public boolean doesCityHaveTheResources(Integer bushelsRequested, Integer bushelsAvailable) {
    return bushelsRequested <= bushelsAvailable;
  }
}
