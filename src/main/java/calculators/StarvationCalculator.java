package calculators;

public class StarvationCalculator {

  public static final int BUSHELS_PER_PERSON = 20;

  public Integer calculateDeaths(Integer citizens, Integer bushels) {
    Integer totalDead = 0;

    Integer bushelsNeeded = citizens * BUSHELS_PER_PERSON;

    int result = bushels - bushelsNeeded;

    if(isShortage(result)){
      totalDead = -(result)/BUSHELS_PER_PERSON;
    }

    return totalDead;
  }

  private boolean isShortage(Integer bushels) {
    return bushels < 0;
  }
}
