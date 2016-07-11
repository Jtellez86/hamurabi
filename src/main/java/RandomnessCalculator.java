import java.util.concurrent.ThreadLocalRandom;

public class RandomnessCalculator {

  public Integer calculateRandomnessBetween(Integer min, Integer max){
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }
}
