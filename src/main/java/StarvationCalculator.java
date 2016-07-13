import static java.lang.Math.abs;

public class StarvationCalculator {

  private final int BUSHELS_NEEDED_PER_PERSON = 20;

  public Integer calculateDeaths(Integer citizens, Integer bushelsUsedForFood) {
    Integer totalDead = 0;

    Integer bushelsNeeded = citizens * BUSHELS_NEEDED_PER_PERSON;

    int result = bushelsUsedForFood - bushelsNeeded;

    if(isShortage(result)){
      totalDead = abs(result)/ BUSHELS_NEEDED_PER_PERSON;
    }

    return totalDead;
  }

  private boolean isShortage(Integer bushels) {
    return bushels < 0;
  }
}
