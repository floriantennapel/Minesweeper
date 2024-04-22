package no.uib.inf101.grid;

/**
 * A grid dimension is a pair of integers representing the number of
 * rows and columns in a grid.
 */
public interface GridDimension {

  /** Number of rows in the grid. */
  int rows();

  /** Number of columns in the grid. */
  int cols();

}
