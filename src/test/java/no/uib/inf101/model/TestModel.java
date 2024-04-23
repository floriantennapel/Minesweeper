package no.uib.inf101.model;

import org.junit.jupiter.api.Test;
import java.util.Random;

import no.uib.inf101.grid.CellPosition;

import static org.junit.jupiter.api.Assertions.*;

public class TestModel {
  private final Random random = new Random();

  @Test
  void sanityTest() {
    Model model = new Model(5, 5, 5);
    assertEquals(model.getGameState(), GameState.START_MENU);
    assertEquals(model.getDimension().rows(), 5);
    assertEquals(model.getDimension().cols(), 5);

    assertEquals(model.getHiddenCells().size(), 25);
  }

  @Test
  void throwsIllegalArgument() {
    assertThrows(IllegalArgumentException.class, () -> new Model(0, 16, 5));
    assertThrows(IllegalArgumentException.class, () -> new Model(5, 5, 26));
    assertThrows(IllegalArgumentException.class, () -> new Model(14, 16, -4));
    assertThrows(IllegalArgumentException.class, () -> new Model(20, 20, 400));
  }

  @Test
  void firstClearIsAlwaysSafe() {
    int iterations = 10000;

    for (int i = 0; i < iterations; i++) {
      Model model = new Model(16, 16, 40);
      CellPosition pos = new CellPosition(random.nextInt(16), random.nextInt(16));
      assertFalse(model.inspectCell(pos));
    }
  }

  @Test
  void cannotInspectFlagged() {
    Model model = new Model(5, 5, 10);
    CellPosition pos = new CellPosition(3, 3);
    model.flagCell(pos);
    assertFalse(model.inspectCell(pos));
    assertEquals(25, model.getHiddenCells().size());
  }

  @Test
  void testReset() {
    Model model = new Model(16, 16, 40);
    CellPosition pos = new CellPosition(5, 5);
    model.inspectCell(pos);

    assertNotEquals(16 * 16, model.getHiddenCells().size());

    int iterations = 1000;
    for (int i = 0; i < iterations; i++) {
      model.resetGame();

      assertEquals(16 * 16, model.getHiddenCells().size());
      assertFalse(model.inspectCell(pos)); // first click is safe, reason why we loop
    }
  }

  @Test
  void testWin() {
    // forcing this board:
    //
    // ***
    // *52
    // *2.

    Model model = new Model(3, 3, 5);
    CellPosition pos = new CellPosition(2, 2);
    assertFalse(model.inspectCell(pos));
    assertEquals(GameState.WIN_SCREEN, model.getGameState());
  }
}
