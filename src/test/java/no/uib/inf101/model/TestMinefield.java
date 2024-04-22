package no.uib.inf101.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMinefield {
  @Test
  void sanityTest() {
    Minefield minefield = new Minefield(5, 5, 0);
    assertEquals(25, minefield.getCells().size());
    assertEquals(".....\n.....\n.....\n.....\n.....", minefield.toString());
  }

  @Test
  void correctAdjacencyNumbers() {
    Minefield minefield = new Minefield(5, 5, """
        ..*..
        *..**
        ....*
        .*.*.
        .*..*""");

    assertEquals("""
        12*32
        *22**
        2234*
        2*3*3
        2*32*""",
        minefield.toString());
  }

  @Test
  void numberOfMines() {
    int iterations = 10000;

    for (int i = 0; i < iterations; i++) {
      Minefield minefield = new Minefield(16, 16, 40);
      assertEquals(
          40,
          minefield.getCells().stream().filter(c -> c.elem().isMine()).count()
      );
    }
  }
}
