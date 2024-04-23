package no.uib.inf101.view;

import no.uib.inf101.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestView {
  // not a lot to test here
  @Test
  void sanityTest() {
    assertThrows(IllegalArgumentException.class, () -> new View(null, 50));
    assertThrows(IllegalArgumentException.class, () -> new View(new Model(10, 10, 10), -40));

    View view = new View(new Model(16, 16, 40), 600);
    assertEquals(600, view.getPreferredSize().width);
  }
}
