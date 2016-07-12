public class AnswerValidator {
  public boolean doesCityHaveTheResources(Integer bushelsRequested, Integer bushelsAvailable) {
    return bushelsRequested <= bushelsAvailable;
  }
}
