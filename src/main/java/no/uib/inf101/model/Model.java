package no.uib.inf101.model;

import no.uib.inf101.controller.ControllableModel;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.view.ViewableModel;

import java.util.*;

public class Model implements ControllableModel, ViewableModel {
  private Minefield minefield;
  private GameState gameState;
  private boolean firstClick;
  private final int rows;
  private final int cols;
  private final int minesOnBoard;

  public Model(int rows, int cols, int minesOnBoard) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("rows and cols must be more than 0");
    }
    if (minesOnBoard > rows * cols) {
      throw new IllegalArgumentException("minesOnBoard cannot be more than number of grid cells");
    }
    if (minesOnBoard < 0) {
      throw new IllegalArgumentException("minesOnBoard cannot be negative");
    }

    minefield = new Minefield(rows, cols, minesOnBoard);
    firstClick = true;
    gameState = GameState.START_MENU;
    this.rows = rows;
    this.cols = cols;
    this.minesOnBoard = minesOnBoard;
  }

  @Override
  public boolean inspectCell(CellPosition pos) {
    if (pos == null) {
      throw new IllegalArgumentException("position cannot be null");
    } else if (!isValidPos(pos)) {
      throw new IllegalArgumentException("invalid position");
    }

    // first click should always hit a blank cell
    if (firstClick) {
      while (minefield.get(pos).isMine() || minefield.get(pos).getAdjacencyCount() != 0) {
        minefield = new Minefield(rows, cols, minesOnBoard);
      }
    }

    MinesweeperCell cell = (MinesweeperCell) minefield.get(pos);

    if (cell.isFlagged()) {
      return false;
    } else if (cell.isMine()) {
      showAllMines();
      return true;
    } else if (cell.getAdjacencyCount() == 0) {
      clearAllBlank(pos);
    } else {
      cell.setVisible();
    }

    firstClick = false;

    if (getHiddenCells().size() == minesOnBoard) {
      setGameState(GameState.WIN_SCREEN);
      resetGame();
    }

    return false;
  }

  @Override
  public void flagCell(CellPosition pos) {
    if (pos == null) {
      throw new IllegalArgumentException("position cannot be null");
    } else if (!isValidPos(pos)) {
      throw new IllegalArgumentException("Position is out of bounds");
    }

    MinesweeperCell cell = (MinesweeperCell) minefield.get(pos);
    cell.toggleFlag();
  }

  @Override
  public void resetGame() {
    minefield = new Minefield(rows, cols, minesOnBoard);
    firstClick = true;
  }

  @Override
  public GridDimension getDimension() {
    return minefield;
  }

  @Override
  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  @Override
  public GameState getGameState() {
    return gameState;
  }

  @Override
  public Iterable<GridCell<IMinesweeperCell>> getMinefield() {
    return minefield.getCells();
  }

  @Override
  public List<GridCell<IMinesweeperCell>> getHiddenCells() {
    List<GridCell<IMinesweeperCell>> hiddenCells = new ArrayList<>();

    for (GridCell<IMinesweeperCell> gc : minefield.getCells()) {
      IMinesweeperCell cell = gc.elem();
      if (!cell.isVisible()) {
        hiddenCells.add(gc);
      }
    }

    return hiddenCells;
  }

  private void showAllMines() {
    for (GridCell<IMinesweeperCell> gc : minefield.getCells()) {
      MinesweeperCell cell = (MinesweeperCell) gc.elem();
      if (cell.isMine()) {
        cell.setVisible();
      }
    }
  }

  // bfs to clear all adjacent empty cells
  private void clearAllBlank(CellPosition pos) {
    Queue<CellPosition> queue = new LinkedList<>();
    queue.add(pos);

    while (!queue.isEmpty()) {
      CellPosition currentPos = queue.remove();
      MinesweeperCell cell = (MinesweeperCell) minefield.get(currentPos);

      // skipping duplicates
      if (cell.isVisible()) {
        continue;
      }

      cell.setVisible();
      queue.addAll(getNextBlank(currentPos));
    }
  }

  private Set<CellPosition> getNextBlank(CellPosition pos) {
    if (minefield.get(pos).getAdjacencyCount() != 0) {
      return new HashSet<>();
    }

    Set<CellPosition> next = new HashSet<>();

    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (i == 0 && j == 0) {
          continue;
        }

        CellPosition posToCheck = new CellPosition(
            pos.row() + i, pos.col() + j
        );

        if (!isValidPos(posToCheck)) {
          continue;
        }

        IMinesweeperCell cell = minefield.get(posToCheck);
        if (!cell.isVisible() && !cell.isMine()) {
          next.add(posToCheck);
        }
      }
    }

    return next;
  }

  private boolean isValidPos(CellPosition pos) {
    int r = pos.row();
    int c = pos.col();

    return r >= 0 && r < minefield.rows() && c >= 0 && c < minefield.cols();
  }
}
