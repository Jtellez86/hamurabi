public class AnswerValidator {
  public boolean isValidAmount(Integer bushelsRequested, Integer bushelsAvailable) {
    return bushelsRequested <= bushelsAvailable;
  }
}
