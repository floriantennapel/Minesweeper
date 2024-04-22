package no.uib.inf101.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

import java.util.Random;

public class Minefield extends Grid<IMinesweeperCell> {
  private final Random random;
  private final int numberOfMines;

  public Minefield(int rows, int cols, int numberOfMines) {
    super(rows, cols);

    if (numberOfMines >= rows * cols) {
      throw new IllegalArgumentException("There cannot be more than, or equal number of mines to number of cells on board");
    }
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("rows/cols must be at least 1");
    }
    if (numberOfMines < 0) {
      throw new IllegalArgumentException("numberOfMines cannot be negative");
    }

    random = new Random();
    this.numberOfMines = numberOfMines;
    boolean[][] mines = createRandomizedBoard();
    initializeBoard(mines);
  }

  // only really used for testing
  // since we want to test that this class works properly this cannot be made into a separate class
  public Minefield(int rows, int cols, String template) {
    super(rows, cols);
    String[] lines = template.split("\n");
    boolean[][] mines = new boolean[rows][cols];
    int mineCount = 0;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        boolean isMine = lines[i].charAt(j) == '*';
        mines[i][j] = isMine;

        if (isMine) {
          ++mineCount;
        }
      }
    }

    this.numberOfMines = mineCount;
    this.random = new Random(); // not used
    initializeBoard(mines);
  }

  private boolean[][] createRandomizedBoard() {
    boolean[][] mines = new boolean[rows()][cols()];
    int minesLeft = numberOfMines;

    // populating board with mines
    while (minesLeft > 0) {
      int row = random.nextInt(0, rows());
      int col = random.nextInt(0, cols());

      if (!mines[row][col]) {
        mines[row][col] = true;
        --minesLeft;
      }
    }

    return mines;
  }

  private void initializeBoard(boolean[][] mines) {
    for (int i = 0; i < rows(); i++) {
      for (int j = 0; j < cols(); j++) {
        int adjacent = countAdjacent(new CellPosition(i, j), mines);
        IMinesweeperCell cell = new MinesweeperCell(mines[i][j], adjacent);

        set(new CellPosition(i, j), cell);
      }
    }
  }

  private int countAdjacent(CellPosition pos, boolean[][] mines) {
    int row = pos.row();
    int col = pos.col();
    int adjacent = 0;

    for (int i = Math.max(row - 1, 0); i < Math.min(row + 2, rows()); i++) {
      for (int j = Math.max(col - 1, 0); j < Math.min(col + 2, cols()); j++) {
        if (i == row && j == col) {
          continue;
        }

        if (mines[i][j]) {
          ++adjacent;
        }
      }
    }

    return adjacent;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < rows(); i++) {
      for (int j = 0; j < cols(); j++) {
        IMinesweeperCell cell = get(new CellPosition(i, j));
        if (cell.isMine()) {
          s.append('*');
        } else if (cell.getAdjacencyCount() == 0) {
          s.append('.');
        } else {
            s.append(cell.getAdjacencyCount());
        }
      }
      s.append('\n');
    }

    // removing last \n
    s.deleteCharAt(s.length() - 1);

    return s.toString();
  }
}
