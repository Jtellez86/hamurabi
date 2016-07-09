import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;


public class QuestionAsker {
  private final InputStream inputStream;
  private final PrintStream output;
  private final AnswerValidator answerValidator;

  QuestionAsker(InputStream in, PrintStream out){
    this.inputStream = in;
    this.output = out;
    this.answerValidator = new AnswerValidator();
  }


  public void ask(Player player, String message) throws IOException {
    output.println(message);
    Integer bushelsToSave = inputStream.read();
    while(!answerValidator.isValidSavingsAmount(bushelsToSave, player.getCity())){
      output.println(message);
      bushelsToSave = inputStream.read();
    }
      player.setBushelsToSave(bushelsToSave);
  }
}
