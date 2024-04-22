package no.uib.inf101.controller;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.model.GameState;
import no.uib.inf101.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseController implements MouseListener, ActionListener {
  private final ControllableModel model;
  private final View view;
  private final Timer timer;

  public MouseController(ControllableModel model, View view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("arguments cannot be null");
    }

    this.model = model;
    this.view = view;

    timer = new Timer(2000, this);
    timer.setInitialDelay(2000);
    timer.setRepeats(false);

    view.addMouseListener(this);
  }

  @Override
  public void mousePressed(MouseEvent mouseEvent) {
    switch (model.getGameState()) {
      case FROZEN -> { return; }
      case ACTIVE -> activeGameController(mouseEvent);
      case START_MENU -> model.setGameState(GameState.ACTIVE);
      case GAME_OVER, WIN_SCREEN -> model.setGameState(GameState.START_MENU);
    }

    view.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    model.setGameState(GameState.GAME_OVER);
    timer.stop();
    model.resetGame();
    view.repaint();
  }

  private void activeGameController(MouseEvent mouseEvent) {
    CellPosition pos = pixelToPos(mouseEvent);

    if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
      boolean hitMine = model.inspectCell(pos);
      if (hitMine) {
        view.repaint();
        waitThenGameOver();
      }
    } else if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
      model.flagCell(pos);
    }
  }

  // converts screen position to grid position
  private CellPosition pixelToPos(MouseEvent mouseEvent) {
    int width = view.getWidth();
    int height = view.getHeight();

    GridDimension dimension = model.getDimension();

    int col = mouseEvent.getX() * dimension.cols() / width;
    int row = mouseEvent.getY() * dimension.rows() / height;

    return new CellPosition(row, col);
  }

  // used to show all mines before going to game-over screen
  private void waitThenGameOver() {
    model.setGameState(GameState.FROZEN);
    timer.start();
  }



  // unused methods

  @Override
  public void mouseClicked(MouseEvent mouseEvent) {}

  @Override
  public void mouseReleased(MouseEvent mouseEvent) {}

  @Override
  public void mouseEntered(MouseEvent mouseEvent) {}

  @Override
  public void mouseExited(MouseEvent mouseEvent) {}
}
