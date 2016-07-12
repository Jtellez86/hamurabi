import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerValidatorTest {

  @Test
  public void shouldNotAllowSavingMoreTotalBushels() throws Exception {
    City city = new City(100);

    AnswerValidator service = new AnswerValidator();

    boolean isValidSavingsAmount = service.doesCityHaveTheResources(20, city.getBushelCount());
    assertThat(isValidSavingsAmount).isTrue();
  }
}