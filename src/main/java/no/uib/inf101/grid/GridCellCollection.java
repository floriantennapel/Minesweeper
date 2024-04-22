package no.uib.inf101.grid;

import java.util.List;

/**
 * Objects in a class extending GridCellCollection can assemble a list
 * of GridCell objects through the getCells() method.
 */
public interface GridCellCollection<T> {

  /**
   * Get a list containing the GridCell objects in this collection
   *
   * @return a list of all GridCell objects in this collection
   */
  List<GridCell<T>> getCells();

}
