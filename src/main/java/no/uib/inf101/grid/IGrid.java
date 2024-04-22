package no.uib.inf101.grid;


public interface IGrid<T> extends GridDimension, GridCellCollection<T> {

  /**
   * Get the value of the cell at the given position.
   *
   * @param pos the position
   * @return the value of the cell
   * @throws IndexOutOfBoundsException if the position is out of bounds
   */
  T get(CellPosition pos);

  /**
   * Set the value of the cell at the given position.
   *
   * @param pos the position
   * @param elem the new value
   * @throws IndexOutOfBoundsException if the position is out of bounds
   */
  void set(CellPosition pos, T elem);

}