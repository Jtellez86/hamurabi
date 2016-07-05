public class AnswerValidator {
  public boolean isValidSavingsAmount(Integer bushelsToBeSaved, City city) {
    return bushelsToBeSaved < city.getBushelCount();
  }
}
