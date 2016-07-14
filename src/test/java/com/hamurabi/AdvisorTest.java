package com.hamurabi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AdvisorTest {

  public static final String FARMER_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d citizens that can farm 3 acres each. Now then, please give me a number.\n";
  public static final String LAND_QUESTION = "How many acres do you wish to buy or sell?(enter a negative amount to sell acres for bushels)";
  public static final String FOOD_QUESTION = "How many bushels do you wish to feed your people?";
  public static final String BUSHEL_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d bushels of grain. Now then, please give me a number.\n";
  public static final String ACREAGE_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d acres of land. Now then, please give me a number.\n";
  public static final String PLANT_QUESTION = "How many acres do you wish to plant with seed? (1 bushel per acre)";

  @Mock
  private BufferedReader mockBufferedReader;

  @Mock
  private PrintStream mockPrintStream;

  private Advisor advisor;
  private City city;

  @Test
  public void shouldAskHowManyBushelsToUseAsFood() throws Exception {
    city = new City(10);
    when(mockBufferedReader.readLine()).thenReturn("10");

    advisor = new Advisor(mockBufferedReader, System.out);

    advisor.askHowMuchToUseForFood(city);

    assertThat(city.getBushelsToUseForFood()).isEqualTo(10);
    assertThat(city.getBushelCount()).isEqualTo(0);
  }

  @Test
  public void shouldNotAllowNegativeBushelsToUseAsFood() throws Exception {
    city = new City(10);
    when(mockBufferedReader.readLine()).thenReturn("-10", "-5", "10");

    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowMuchToUseForFood(city);

    verify(mockPrintStream, times(2)).printf(BUSHEL_CORRECTION_MESSAGE, 10);
    verify(mockPrintStream, times(3)).println(FOOD_QUESTION);
  }

  @Test
  public void shouldAskHowMuchToUseAsFoodIfAnswerIsInvalid() throws Exception {
    city = new City(10);
    when(mockBufferedReader.readLine()).thenReturn("20", "15", "10");
    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowMuchToUseForFood(city);

    verify(mockPrintStream, times(3)).println(FOOD_QUESTION);
    verify(mockPrintStream, times(2)).printf(BUSHEL_CORRECTION_MESSAGE, 10);
  }

  @Test
  public void shouldAskFoodQuestionAgainIfInputIsNonInteger() throws Exception {
    city = new City(10);
    when(mockBufferedReader.readLine()).thenReturn("f", "f", "10");
    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowMuchToUseForFood(city);

    verify(mockPrintStream, times(3)).println("How many bushels do you wish to feed your people?");
    verify(mockPrintStream, times(2)).printf(BUSHEL_CORRECTION_MESSAGE, 10);
  }

  @Test
  public void shouldAskHowMuchToPlant() throws Exception {
    city = new City(50);
    when(mockBufferedReader.readLine()).thenReturn("20");
    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowManyBushelsToPlant(city);

    assertThat(city.getBushelCount()).isEqualTo(30);
    assertThat(city.getBushelsToUseForPlanting()).isEqualTo(20);
  }

  @Test
  public void shouldAskHowMuchToPlantAgainIfAnswerIsInvalid() throws Exception {
    city = new City(10);
    when(mockBufferedReader.readLine()).thenReturn("20", "f", "10");
    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowManyBushelsToPlant(city);

    verify(mockPrintStream, times(3)).println(PLANT_QUESTION);
    verify(mockPrintStream, times(2)).printf(BUSHEL_CORRECTION_MESSAGE, 10);
  }

  @Test
  public void shouldNotAllow1CitizenToFarmMoreThan3Acres() throws Exception {
    city = new City(10);
    city.setPopulation(1);

    when(mockBufferedReader.readLine()).thenReturn("10", "4", "3");

    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowManyBushelsToPlant(city);

    verify(mockPrintStream, times(3)).println(PLANT_QUESTION);
    verify(mockPrintStream, times(2)).printf(FARMER_CORRECTION_MESSAGE, 1);
  }

  @Test
  public void shouldNotAllowPlantingIfNotEnoughAcres() throws Exception {
    city = new City(15);
    city.setPopulation(5);
    city.setAcreage(10);

    when(mockBufferedReader.readLine()).thenReturn("15", "11", "10");

    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowManyBushelsToPlant(city);

    verify(mockPrintStream, times(3)).println(PLANT_QUESTION);
    verify(mockPrintStream, times(2)).printf(ACREAGE_CORRECTION_MESSAGE, 10);

    assertThat(city.getAcreageToFarm()).isEqualTo(10);
    assertThat(city.getAcreage()).isEqualTo(10);
  }

  @Test
  public void shouldAskPlayerToTradePlayerChoosesSellAcreage() throws Exception {
    city = new City(20);
    city.setValueOfLandInBushels(20);
    when(mockBufferedReader.readLine()).thenReturn("-1");
    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowMuchLandToTrade(city);

    assertThat(city.getBushelCount()).isEqualTo(40);
    assertThat(city.getAcreage()).isEqualTo(999);
    assertThat(city.getAcresToTrade()).isEqualTo(1);

    verify(mockPrintStream).println(LAND_QUESTION);
  }

  @Test
  public void shouldNotAllowTradingLandIfLandIsBeingUsedToFarm() throws Exception {
    city = new City(20);
    city.setValueOfLandInBushels(20);
    city.setAcreage(20);
    city.setAcreageToFarm(20);
    when(mockBufferedReader.readLine()).thenReturn("-20", "0");

    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowMuchLandToTrade(city);

    assertThat(city.getBushelCount()).isEqualTo(20);
    assertThat(city.getAcreage()).isEqualTo(20);
    assertThat(city.getAcresToTrade()).isEqualTo(0);

    verify(mockPrintStream, times(2)).println(LAND_QUESTION);
    verify(mockPrintStream, times(1)).printf(ACREAGE_CORRECTION_MESSAGE, 0);
  }

  @Test
  public void shouldAllowNotTrading() throws Exception {
    city = new City(20);
    city.setValueOfLandInBushels(20);
    when(mockBufferedReader.readLine()).thenReturn("0");
    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowMuchLandToTrade(city);

    assertThat(city.getBushelCount()).isEqualTo(20);
    assertThat(city.getAcreage()).isEqualTo(1000);
    assertThat(city.getAcresToTrade()).isEqualTo(0);

    verify(mockPrintStream).println(LAND_QUESTION);
  }

  @Test
  public void shouldAskHowMuchToTradeAgainWhenGivenIncorrectNegativeAmount() throws Exception {
    city = new City(1000);
    city.setValueOfLandInBushels(20);
    when(mockBufferedReader.readLine()).thenReturn("-1010", "-1005", "-1000");
    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowMuchLandToTrade(city);

    verify(mockPrintStream, times(3)).println(LAND_QUESTION);
    verify(mockPrintStream, times(2)).printf(ACREAGE_CORRECTION_MESSAGE, 1000);
  }

  @Test
  public void shouldNotAllowPurchaseOfLandWithInsufficientBushels() throws Exception {
    city = new City(1000);
    city.setValueOfLandInBushels(20);
    when(mockBufferedReader.readLine()).thenReturn("60", "55", "50");
    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    advisor.askHowMuchLandToTrade(city);

    verify(mockPrintStream, times(3)).println(LAND_QUESTION);
    verify(mockPrintStream, times(2)).printf(BUSHEL_CORRECTION_MESSAGE, 1000);
  }

  @Test
  public void shouldRejectNonIntegerInput() throws Exception {
    advisor = new Advisor(mockBufferedReader, mockPrintStream);

    boolean isValid = advisor.isAnInteger("invalidInput");

    assertThat(isValid).isFalse();
  }
}