package no.uib.inf101.model;

public class MinesweeperCell implements IMinesweeperCell {
  private final boolean isMine;
  private final int adjacencyCount;
  private boolean visible;
  private boolean flagged;

  public MinesweeperCell(boolean isMine, int adjacencyCount) {
    if (adjacencyCount < 0 || adjacencyCount > 8) {
      throw new IllegalArgumentException("Invalid adjacencyCount");
    }

    this.isMine = isMine;
    this.adjacencyCount = adjacencyCount;

    visible = false;
    flagged = false;
  }

  @Override
  public boolean isMine() {
    return isMine;
  }

  @Override
  public int getAdjacencyCount() {
    return adjacencyCount;
  }

  @Override
  public boolean isFlagged() {
    return flagged;
  }

  @Override
  public boolean isVisible() {
    return visible;
  }

  void setVisible() {
    visible = true;
  }

  void toggleFlag() {
    flagged = !flagged;
  }
}
