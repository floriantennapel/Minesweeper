package no.uib.inf101.model;

public class MovableEntity {
  private Vector2D position;
  private Vector2D direction;
  private Vector2D viewport;

  public MovableEntity(Vector2D position) {
    this.position = position;
    direction = new Vector2D(0.0, 1.0);
    viewport = direction.perpendicular();
  }

  public void move(Direction dir, double moveScalar) {
    Vector2D directionVector = switch (dir) {
      case FORWARD -> direction;
      case BACKWARD -> direction.scaled(-1.0);
      case LEFT -> direction.perpendicular().scaled(-1.0);
      case RIGHT -> direction.perpendicular();
    };

    position = position.add(directionVector.scaled(moveScalar));
  }

  public void rotate(boolean trueForClockwise, double moveScalar) {
    direction = direction.rotated(trueForClockwise ? moveScalar : -moveScalar);
    viewport = direction.perpendicular();
  }

  public Vector2D getDirection() {
    return direction;
  }

  public Vector2D getPosition() {
    return position;
  }

  public Vector2D getViewport() {
    return viewport;
  }
}
