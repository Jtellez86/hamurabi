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

  @Mock
  InputStream mockInputStream;
  @Mock
  PrintStream mockPrintStream;

  QuestionAsker asker;
  Player player;

  @Test
  public void shouldAskHowManyBushelsToUseAsFood() throws Exception {
    player = new Player(new City(10));
    when(mockInputStream.read()).thenReturn(10);

    asker = new QuestionAsker(mockInputStream, System.out);

    asker.askHowMuchToUseForFood(player);

    assertThat(player.getBushelsToSave()).isEqualTo(10);
    assertThat(player.getCity().getBushelCount()).isEqualTo(0);
  }

  @Test
  public void shouldAskForCorrectionIfHowMuchAsFoodInputIsInvalid() throws Exception {
    player = new Player(new City(10));
    when(mockInputStream.read()).thenReturn(20, 15, 10);
    asker = new QuestionAsker(mockInputStream, mockPrintStream);

    asker.askHowMuchToUseForFood(player);

    verify(mockPrintStream, times(3)).println("How many bushels do you wish to feed your people?");
  }

  @Test
  public void shouldAskHowMuchToPlant() throws Exception {
    player = new Player(new City(50));
    when(mockInputStream.read()).thenReturn(20);
    asker = new QuestionAsker(mockInputStream, mockPrintStream);

    asker.askHowMuchToPlant(player);

    assertThat(player.getCity().getBushelCount()).isEqualTo(30);
  }

  @Test
  public void shouldAskHowMuchToPlantTwiceIfAnswerIsInvalid() throws Exception {
    player = new Player(new City(10));
    when(mockInputStream.read()).thenReturn(20, 15, 10);
    asker = new QuestionAsker(mockInputStream, mockPrintStream);

    asker.askHowMuchToPlant(player);

    verify(mockPrintStream, times(3)).println("How many acres do you wish to plant with seed?");
  }

}