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
public class QuestionAskerTest {

  public static final String LAND_QUESTION = "How many acres do you wish to buy or sell?(enter a negative amount to sell acres for bushels)";
  public static final String BUSHEL_CORRECTION_MESSAGE = "com.hamurabi.Hamurabi: think again, o mighty master, you have only %d bushels of grain. Now then, please give me a number.%n";
  public static final String ACREAGE_CORRECTION_MESSAGE = "com.hamurabi.Hamurabi: think again, o mighty master, you have only %d acres of land. Now then, please give me a number.%n";

  @Mock
  private BufferedReader mockBufferedReader;

  @Mock
  private PrintStream mockPrintStream;

  private QuestionAsker asker;
  private City city;

  @Test
  public void shouldAskHowManyBushelsToUseAsFood() throws Exception {
    city = new City(10);
    when(mockBufferedReader.readLine()).thenReturn("10");

    asker = new QuestionAsker(mockBufferedReader, System.out);

    asker.askHowMuchToUseForFood(city);

    assertThat(city.getBushelsToUseForFood()).isEqualTo(10);
    assertThat(city.getBushelCount()).isEqualTo(0);
  }

  @Test
  public void shouldAskHowMuchToUseAsFoodIfAnswerIsInvalid() throws Exception {
    city = new City(10);
    when(mockBufferedReader.readLine()).thenReturn("20", "15", "10");
    asker = new QuestionAsker(mockBufferedReader, mockPrintStream);

    asker.askHowMuchToUseForFood(city);

    verify(mockPrintStream, times(3)).println("How many bushels do you wish to feed your people?");
    verify(mockPrintStream, times(2)).printf(BUSHEL_CORRECTION_MESSAGE, 10);
  }

  @Test
  public void shouldAskFoodQuestionAgainIfInputIsNonInteger() throws Exception {
    city = new City(10);
    when(mockBufferedReader.readLine()).thenReturn("f", "f", "10");
    asker = new QuestionAsker(mockBufferedReader, mockPrintStream);

    asker.askHowMuchToUseForFood(city);

    verify(mockPrintStream, times(3)).println("How many bushels do you wish to feed your people?");
    verify(mockPrintStream, times(2)).printf(BUSHEL_CORRECTION_MESSAGE, 10);
  }

  @Test
  public void shouldAskHowMuchToPlant() throws Exception {
    city = new City(50);
    when(mockBufferedReader.readLine()).thenReturn("20");
    asker = new QuestionAsker(mockBufferedReader, mockPrintStream);

    asker.askHowManyBushelsToPlant(city);

    assertThat(city.getBushelCount()).isEqualTo(30);
    assertThat(city.getBushelsToUseForPlanting()).isEqualTo(20);
  }

  @Test
  public void shouldAskHowMuchToPlantAgainIfAnswerIsInvalid() throws Exception {
    city = new City(10);
    when(mockBufferedReader.readLine()).thenReturn("20", "f", "10");
    asker = new QuestionAsker(mockBufferedReader, mockPrintStream);

    asker.askHowManyBushelsToPlant(city);

    verify(mockPrintStream, times(3)).println("How many acres do you wish to plant with seed?");
    verify(mockPrintStream, times(2)).printf(BUSHEL_CORRECTION_MESSAGE, 10);
  }

  @Test
  public void shouldAskPlayerToTradePlayerChoosesSellAcreage() throws Exception {
    city = new City(20);
    city.setValueOfLandInBushels(20);
    when(mockBufferedReader.readLine()).thenReturn("-1");
    asker = new QuestionAsker(mockBufferedReader, mockPrintStream);

    asker.askHowMuchLandToTrade(city);

    assertThat(city.getBushelCount()).isEqualTo(40);
    assertThat(city.getAcreage()).isEqualTo(999);
    assertThat(city.getAcresToTrade()).isEqualTo(1);

    verify(mockPrintStream).println(LAND_QUESTION);
  }

  @Test
  public void shouldAskHowMuchToTradeAgainWhenGivenIncorrectPositiveAmount() throws Exception {
    city = new City(1000);
    city.setValueOfLandInBushels(20);
    when(mockBufferedReader.readLine()).thenReturn("1010", "1005", "1000");
    asker = new QuestionAsker(mockBufferedReader, mockPrintStream);

    asker.askHowMuchLandToTrade(city);

    verify(mockPrintStream, times(3)).println(LAND_QUESTION);
    verify(mockPrintStream, times(2)).printf(ACREAGE_CORRECTION_MESSAGE, 1000);
  }

  @Test
  public void shouldAskHowMuchToTradeAgainWhenGivenIncorrectNegativeAmount() throws Exception {
    city = new City(1000);
    city.setValueOfLandInBushels(20);
    when(mockBufferedReader.readLine()).thenReturn("-1010", "-1005", "-1000");
    asker = new QuestionAsker(mockBufferedReader, mockPrintStream);

    asker.askHowMuchLandToTrade(city);

    verify(mockPrintStream, times(3)).println(LAND_QUESTION);
    verify(mockPrintStream, times(2)).printf(ACREAGE_CORRECTION_MESSAGE, 1000);
  }

  @Test
  public void shouldRejectNonIntegerInput() throws Exception {
    asker = new QuestionAsker(mockBufferedReader, mockPrintStream);

    boolean isValid = asker.isInteger("invalidInput");

    assertThat(isValid).isFalse();
  }

  @Test
  public void shouldNotAllowAllocationAmountGreaterThanSurplus() throws Exception {
    City city = new City(10);
    asker = new QuestionAsker(mockBufferedReader, mockPrintStream);

    boolean isValidSavingsAmount = asker.doesCityHaveTheResources(20, city.getBushelCount());
    assertThat(isValidSavingsAmount).isFalse();
  }
}