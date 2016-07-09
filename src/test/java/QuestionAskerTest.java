import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class QuestionAskerTest {

  @Mock
  InputStream mockInputStream;
  @Mock
  PrintStream mockPrintStream;

  QuestionAsker asker;
  Player player;
  @Before
  public void setUp() throws Exception {
    player = new Player(new City(15));

  }

  @Test
  public void shouldAskHowManyBushelsToSave() throws Exception {
    when(mockInputStream.read()).thenReturn(10);

    asker = new QuestionAsker(mockInputStream, System.out);

    asker.ask(player, "How many bushels do you wish to feed your people?");

    assertThat(player.getBushelsToSave()).isEqualTo(10);
  }

  @Test
  public void shouldAskForCorrectionIfInputIsInvalid() throws Exception {
    when(mockInputStream.read()).thenReturn(20, 15, 10);
    asker = new QuestionAsker(mockInputStream, mockPrintStream);

    asker.ask(player, "How many bushels do you wish to feed your people?");

    verify(mockPrintStream, times(3)).println("How many bushels do you wish to feed your people?");

  }

}