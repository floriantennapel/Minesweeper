package no.uib.inf101.model;

public interface IMinesweeperCell {
  /** Is this a cell containing a mine? */
  boolean isMine();

  /** @return Number of mines adjacent to this */
  int getAdjacencyCount();

  /** Is this currently marked with a flag? */
  boolean isFlagged();

  /** Should this be shown, if not it will be covered up */
  boolean isVisible();
}
