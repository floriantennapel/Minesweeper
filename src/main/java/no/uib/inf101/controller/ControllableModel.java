package no.uib.inf101.controller;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.model.GameState;

public interface ControllableModel {

  /**
   * Make a MinesweeperCell visible
   * @param pos position of cell that should be made visible
   * @return true if the cell was a bomb, false otherwise
   * @throws IllegalArgumentException when pos is null or out of bounds
   */
  boolean inspectCell(CellPosition pos);

  /**
   * Put a flag on a MinesweeperCell
   * <p/>
   * If the cell was already flagged, the flag is removed
   * @param pos position of cell that should be flagged
   * @throws IllegalArgumentException when pos if null or out of bounds
   */
  void flagCell(CellPosition pos);

  /**
   * Create a new minefield game instance
   */
  void resetGame();

  /**
   * get dimension of Minesweeper model
   * @return GridDimension object containing number of rows and columns
   */
  GridDimension getDimension();

  /**
   * Change into specified game state
   * @param gameState one of the options specified by the GameState enum
   */
  void setGameState(GameState gameState);

  /**
   * Current state of the game
   * @return one of the values specified by the GameState enum
   * */
  GameState getGameState();
}
