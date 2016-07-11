package calculators;

import java.io.PrintStream;

public class StarvationCalculator {

  private final int BUSHELS_PER_PERSON = 20;
  private final PrintStream output;

  public StarvationCalculator(PrintStream output) {
    this.output = output;
  }


  public Integer calculateDeaths(Integer citizens, Integer bushels) {
    Integer totalDead = 0;

    Integer bushelsNeeded = citizens * BUSHELS_PER_PERSON;

    int result = bushels - bushelsNeeded;

    if(isShortage(result)){
      totalDead = -(result)/BUSHELS_PER_PERSON;
      output.printf("You starved %d people in one year!!!%n", totalDead);
    }

    return totalDead;
  }

  private boolean isShortage(Integer bushels) {
    return bushels < 0;
  }
}
