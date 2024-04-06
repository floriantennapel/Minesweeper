package no.uib.inf101.model;

// If we want to do any mathematical operations with generic numeric classes we first have to cast them to some primitive.
// Since this is the case, we might as well make double the standard type for the values.
public record Vector2D(double x, double y) {
  /** Result of vector addition between this and v */
  public Vector2D add(Vector2D v) {
    return new Vector2D(x + v.x, y + v.y);
  }

  /** result of multiplying this with scalar */
  public Vector2D scaled(double scalar) {
    return new Vector2D(x * scalar, y * scalar);
  }

  /**
   * @param angle in radians
   * @return a new Vector2D rotated by angle
   */
  public Vector2D rotated(double angle) {
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);

    return new Vector2D(
      x * cos - y * sin,
      x * sin + y * cos
    );
  }

  /** @return a copy of this rotated by 90 degrees */
  public Vector2D perpendicular() {
    return new Vector2D(-y, x);
  }
}
