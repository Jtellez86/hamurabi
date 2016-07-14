package com.hamurabi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.in;
import static java.lang.System.out;

public class Hamurabi {

  private City city;
  private Advisor advisor;
  private EndGameCalculator endGame;

  public Hamurabi() throws IOException {
    this.city = new City(2800);
    this.advisor = new Advisor(new BufferedReader(new InputStreamReader(in)), out);
    this.endGame = new EndGameCalculator();
  }

  public void runGameLoop() throws IOException {
    boolean loser = false;
    for (int year = 1; year < 11; year++) {
      city.startYear();
      advisor.giveYearlyUpdate(city, year);

      advisor.askHowMuchToUseForFood(city);
      advisor.askHowManyBushelsToPlant(city);
      advisor.askHowMuchLandToTrade(city);

      city.calculateDeaths();

      if (endGame.isGameOver(city)) {
        loser = true;
        advisor.impeach();
        break;
      }

      if(city.isPlague()){
        advisor.informOfPlague();
        city.setPopulation( (city.getPopulation()/2) );
      }
    }
    if(!loser)
    printEndGameMessage(city);
  }

  public void printEndGameMessage(City city) {
    out.println("\nCongratulations! You have managed to serve this city for 10 years without impeachment!\n");
    out.printf("Your city has of %d citizens and you have %d bushels of food in surplus\n", city.getPopulation(), city.getBushelCount());
    out.println("\nWell done!\n");

  }
}
