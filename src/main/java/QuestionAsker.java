import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;


public class QuestionAsker {
  public static final String FOOD_QUESTION = "How many bushels do you wish to feed your people?";
  public static final String PLANT_QUESTION = "How many acres do you wish to plant with seed?";
  private final InputStream inputStream;
  private final PrintStream output;
  private final AnswerValidator answerValidator;

  QuestionAsker(InputStream in, PrintStream out){
    this.inputStream = in;
    this.output = out;
    this.answerValidator = new AnswerValidator();
  }


  public void askHowMuchToUseForFood(Player player) throws IOException {
    output.println(FOOD_QUESTION);
    Integer bushelsToEat = inputStream.read();
    while(!answerValidator.isValidAmount(bushelsToEat, player.getCity().getBushelCount())){
      output.println(FOOD_QUESTION);
      bushelsToEat = inputStream.read();
    }
      player.setBushelsToEat(bushelsToEat);
      player.getCity().setBushelCount(player.getCity().getBushelCount() - bushelsToEat);
  }


  public void askHowMuchToPlant(Player player) throws IOException {
    output.println(PLANT_QUESTION);
    Integer bushelsToPlant = inputStream.read();
    while(!answerValidator.isValidAmount(bushelsToPlant, player.getCity().getBushelCount())){
      output.println(PLANT_QUESTION);
      bushelsToPlant = inputStream.read();
    }
    player.getCity().setBushelCount(player.getCity().getBushelCount() - bushelsToPlant);
  }
}
