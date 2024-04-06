package no.uib.inf101.model;

import java.util.ArrayList;
import java.util.List;

public class Model {
  private final List<MovableEntity> entities;

  public Model() {
    entities = new ArrayList<>();
    entities.add(new MovableEntity(new Vector2D(4.5, 1.5)));
  }

  public int[] getDimension() {
    return new int[] {9, 9};
  }

  public MovableEntity getPlayer() {
    return entities.get(0);
  }
}
