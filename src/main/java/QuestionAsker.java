import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;


public class QuestionAsker {
  public static final String FOOD_QUESTION = "How many bushels do you wish to feed your people?";
  public static final String PLANT_QUESTION = "How many acres do you wish to plant with seed?";
  public static final String BUSHEL_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d bushels of grain. Now then, %n";
  public static final String ACREAGE_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d acres of land. Now then, %n";
  public static final String LAND_QUESTION = "How many acres do you wish to buy or sell?(enter a negative amount to sell acres for bushels)";
  private final InputStream inputStream;
  private final PrintStream output;
  private final AnswerValidator answerValidator;
  private BushelTrader trader;

  QuestionAsker(InputStream in, PrintStream out, RandomnessCalculator calculator) {
    this.inputStream = in;
    this.output = out;
    this.answerValidator = new AnswerValidator();
    this.trader = new BushelTrader(calculator);
  }


  public void askHowMuchToUseForFood(Player player) throws IOException {
    output.println(FOOD_QUESTION);
    Integer bushelsToEat = inputStream.read();
    while (!answerValidator.doesCityHaveTheResources(bushelsToEat, player.getCity().getBushelCount())) {
      output.printf(BUSHEL_CORRECTION_MESSAGE, player.getCity().getBushelCount());
      output.println(FOOD_QUESTION);
      bushelsToEat = inputStream.read();
    }
    player.setBushelsToEat(bushelsToEat);
    player.getCity().setBushelCount(player.getCity().getBushelCount() - bushelsToEat);
  }


  public void askHowMuchToPlant(Player player) throws IOException {
    output.println(PLANT_QUESTION);
    Integer bushelsToPlant = inputStream.read();
    while (!answerValidator.doesCityHaveTheResources(bushelsToPlant, player.getCity().getBushelCount())) {
      output.printf(BUSHEL_CORRECTION_MESSAGE, player.getCity().getBushelCount());
      output.println(PLANT_QUESTION);
      bushelsToPlant = inputStream.read();
    }
    player.getCity().setBushelCount(player.getCity().getBushelCount() - bushelsToPlant);
  }

  public void askHowMuchLandToTrade(Player player) throws IOException {
    output.println(LAND_QUESTION);
    Integer acresToTrade = inputStream.read();

    while (!answerValidator.doesCityHaveTheResources(Math.abs(acresToTrade), player.getCity().getAcreage())) {
      output.printf(ACREAGE_CORRECTION_MESSAGE, player.getCity().getAcreage());
      output.println(LAND_QUESTION);
      acresToTrade = inputStream.read();
    }
    if (acresToTrade < 0) {
        trader.sellAcreage(Math.abs(acresToTrade), player.getCity());
    } else {
      trader.buyAcreage(acresToTrade, player.getCity());
    }
  }
}
