package no.uib.inf101.model;

import org.junit.jupiter.api.Test;
import java.util.Random;

import no.uib.inf101.grid.CellPosition;

import static org.junit.jupiter.api.Assertions.*;

public class TestModel {
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
  }

  @Test
  void firstClearIsAlwaysSafe() {
    Random random = new Random();
    int iterations = 10000;

    for (int i = 0; i < iterations; i++) {
      Model model = new Model(16, 16, 40);
      CellPosition pos = new CellPosition(random.nextInt(16), random.nextInt(16));
      assertFalse(model.inspectCell(pos));
    }
  }
}
