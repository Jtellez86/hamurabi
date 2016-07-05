package calculators;

import java.util.Random;

public class RatLossCalculator {

  Random numberGenerator;

  public RatLossCalculator() {
    this.numberGenerator = new Random();
  }

  public Integer calculateLoss() {
    return numberGenerator.nextInt(100);
  }
}
