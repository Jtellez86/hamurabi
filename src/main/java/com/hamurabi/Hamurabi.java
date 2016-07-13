package com.hamurabi;

import com.hamurabi.exceptions.TerribleRulerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.*;
import static java.lang.System.out;

public class Hamurabi {

  private City city;
  private QuestionAsker asker;
  private EndGameCalculator endGame;

  public Hamurabi() throws IOException {
    this.city = new City(2800);
    this.asker = new QuestionAsker(new BufferedReader(new InputStreamReader(in)), out);
    this.endGame = new EndGameCalculator();
  }

  public void runGameLoop() throws IOException{
    city.startYear();
    for(int year= 1; year < 11; year++){
      printMOTY(city, year);
      asker.askHowMuchToUseForFood(city);
      asker.askHowManyBushelsToPlant(city);
      asker.askHowMuchLandToTrade(city);

      city.endYear();

      if(city.getDeathCount() > 0){
        out.printf("You starved %d people in one year!!!%n", city.getDeathCount());
      }

      try {
        endGame.isGameOver(city);
      } catch (TerribleRulerException e) {
        out.println("Due to this extreme mismanagement, you have not only been impeached and thrown out of office, but you have also been declared 'National Fink'!");
        break;
      }
    }
    printEndGameMessage(city);

  }

  public void printMOTY(City city, Integer yearsRuled) {
    out.println("\nHammurabi: I beg to report to you,\n");
    out.printf("In Year %d, %d people starved.\n", yearsRuled, city.getDeathCount());
    out.printf("%d people came to the city.\n", city.getNewCitizens());
    out.printf("The city population is now %d.\n", city.getPopulation());
    out.printf("The city now owns %d acres.\n", city.getAcreage());
    out.printf("You harvested %d bushels per acre\n", city.getBushelsPerAcre());
    out.printf("Rats ate %d bushels\n", city.getBushelsEatenByRats());
    out.printf("You now have %d bushels in store\n", city.getBushelCount());
    out.printf("Land is trading at %d bushels per acre\n", city.getValueOfLandInBushels());
  }

  public void printEndGameMessage(City city) {
    out.println("\nCongratulations! You have managed to serve this city for 10 years without impeachment!\n");
    out.printf("Your city has of %d citizens and you have %d bushels of food in surplus\n", city.getPopulation(), city.getBushelCount());
    out.println("\nWell done!\n");

  }
}
