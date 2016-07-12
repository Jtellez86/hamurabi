import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class QuestionAskerTest {

  public static final String LAND_QUESTION = "How many acres do you wish to buy or sell?(enter a negative amount to sell acres for bushels)";
  public static final String BUSHEL_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d bushels of grain. Now then, %n";
  public static final String ACREAGE_CORRECTION_MESSAGE = "Hamurabi: think again, o mighty master, you have only %d acres of land. Now then, %n";

  @Mock
  InputStream mockInputStream;

  @Mock
  PrintStream mockPrintStream;

  @Mock
  RandomnessCalculator calculator;

  QuestionAsker asker;
  Player player;

  @Test
  public void shouldAskHowManyBushelsToUseAsFood() throws Exception {
    player = new Player(new City(10));
    when(mockInputStream.read()).thenReturn(10);

    asker = new QuestionAsker(mockInputStream, System.out, calculator);

    asker.askHowMuchToUseForFood(player);

    assertThat(player.getBushelsForFood()).isEqualTo(10);
    assertThat(player.getCity().getBushelCount()).isEqualTo(0);
  }

  @Test
  public void shouldAskHowMuchToUseAsFoodIfAnswerIsInvalid() throws Exception {
    player = new Player(new City(10));
    when(mockInputStream.read()).thenReturn(20, 15, 10);
    asker = new QuestionAsker(mockInputStream, mockPrintStream, calculator);

    asker.askHowMuchToUseForFood(player);

    verify(mockPrintStream, times(3)).println("How many bushels do you wish to feed your people?");
    verify(mockPrintStream, times(2)).printf("Hamurabi: think again, o mighty master, you have only %d bushels of grain. Now then, %n", 10);
  }

  @Test
  public void shouldAskHowMuchToPlant() throws Exception {
    player = new Player(new City(50));
    when(mockInputStream.read()).thenReturn(20);
    asker = new QuestionAsker(mockInputStream, mockPrintStream, calculator);

    asker.askHowMuchToPlant(player);

    assertThat(player.getCity().getBushelCount()).isEqualTo(30);
  }

  @Test
  public void shouldAskHowMuchToPlantAgainIfAnswerIsInvalid() throws Exception {
    player = new Player(new City(10));
    when(mockInputStream.read()).thenReturn(20, 15, 10);
    asker = new QuestionAsker(mockInputStream, mockPrintStream, calculator);

    asker.askHowMuchToPlant(player);

    verify(mockPrintStream, times(3)).println("How many acres do you wish to plant with seed?");
    verify(mockPrintStream, times(2)).printf(BUSHEL_CORRECTION_MESSAGE, 10);
  }

  @Test
  public void shouldAskPlayerToSellOrBuyLandPlayerChoosesSellAcreage() throws Exception {
    player = new Player(new City(20));
    when(mockInputStream.read()).thenReturn(-1);
    when(calculator.calculateRandomnessBetween(17,26)).thenReturn(20);
    asker = new QuestionAsker(mockInputStream, mockPrintStream, calculator);

    asker.askHowMuchLandToTrade(player);

    assertThat(player.getCity().getBushelCount()).isEqualTo(40);
    assertThat(player.getCity().getAcreage()).isEqualTo(999);

    verify(mockPrintStream).println(LAND_QUESTION);
  }

  @Test
  public void shouldAskHowMuchToTradeAgainWhenGivenIncorrectPositiveAmount() throws Exception {
    player = new Player(new City(20));
    when(mockInputStream.read()).thenReturn(1010, 1005, 1000);
    when(calculator.calculateRandomnessBetween(17,26)).thenReturn(20);
    asker = new QuestionAsker(mockInputStream, mockPrintStream, calculator);

    asker.askHowMuchLandToTrade(player);

    verify(mockPrintStream, times(3)).println(LAND_QUESTION);
    verify(mockPrintStream, times(2)).printf(ACREAGE_CORRECTION_MESSAGE, 1000);
  }

  @Test
  public void shouldAskHowMuchToTradeAgainWhenGivenIncorrectNegativeAmount() throws Exception {
    player = new Player(new City(20));
    when(mockInputStream.read()).thenReturn(-1010, -1005, -1000);
    when(calculator.calculateRandomnessBetween(17,26)).thenReturn(20);
    asker = new QuestionAsker(mockInputStream, mockPrintStream, calculator);

    asker.askHowMuchLandToTrade(player);

    verify(mockPrintStream, times(3)).println(LAND_QUESTION);
    verify(mockPrintStream, times(2)).printf(ACREAGE_CORRECTION_MESSAGE, 1000);
  }
}