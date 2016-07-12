import java.io.IOException;

import static java.lang.System.out;

public class Hamurabi {

  private City city;
  private QuestionAsker asker;

  public Hamurabi() throws IOException {
    this.city = new City(2800);
    this.asker = new QuestionAsker(System.in, System.out, new RandomnessCalculator());
    gameLoop();
  }

  private void gameLoop() throws IOException{
    city.initializeCity();
    for(int year= 1; year < 11; year++){
      printMOTY(city, 1);
      asker.askHowMuchToUseForFood(city);
    }
  }

  public void printMOTY(City city, Integer yearsRuled) {
    out.println("Hammurabi: I beg to report to you,\n");
    out.printf("In Year %d, %d people starved.\n", yearsRuled, city.getDeathCount());
    out.printf("%d people came to the city.\n", city.getNewCitizens());
    out.printf("The city population is now %d.\n", city.getPopulation());
    out.printf("The city now owns %d acres.\n", city.getAcreage());
    out.printf("You harvested %d per acre\n", city.getBushelsHarvested());
    out.printf("Rats ate %d bushels\n", city.getBushelsEatenByRats());
    out.printf("You now have %d bushels in store\n", city.getBushelCount());
    out.printf("Land is trading at %d bushels per acre\n", city.getValueOfLandInBushels());
  }
}
