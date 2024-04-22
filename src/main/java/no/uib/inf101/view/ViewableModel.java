package no.uib.inf101.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.model.GameState;
import no.uib.inf101.model.IMinesweeperCell;

public interface ViewableModel {
  /**
   * get dimension of Minesweeper model
   * @return GridDimension object containing number of rows and columns
   */
  GridDimension getDimension();

  /**
   * Get all cells on game board
   * @return Every IMinesweeperCell object on the board
   */
  Iterable<GridCell<IMinesweeperCell>> getMinefield();

  /** Get all cells that should be hidden */
  Iterable<GridCell<IMinesweeperCell>> getHiddenCells();

  /**
   * Current state of the game
   * @return one of the values specified by the GameState enum
   * */
  GameState getGameState();
}
