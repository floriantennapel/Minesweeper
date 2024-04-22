package no.uib.inf101.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.model.IMinesweeperCell;
import no.uib.inf101.model.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;

public class View extends JPanel {
  private static final int RIDGE_WIDTH = 8;
  private final Model model;

  public View(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }

    this.model = model;

    setPreferredSize(new Dimension(600, 600));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    switch (model.getGameState()) {
      case ACTIVE, FROZEN -> paintActiveGame(g2);
      case START_MENU -> paintMenu(g2, "Minesweeper");
      case GAME_OVER -> paintMenu(g2, "Game Over");
      case WIN_SCREEN -> paintMenu(g2, "You Won!");
    }
  }

  private void paintActiveGame(Graphics2D g) {
    drawBackgroundGrid(g);
    drawGameBoard(g);
    coverHiddenCells(g);
  }

  private void paintMenu(Graphics2D g, String text) {
    g.setBackground(Color.LIGHT_GRAY);
    g.setFont(new Font(text, Font.BOLD, 40));
    Inf101Graphics.drawCenteredString(g, text, this.getWidth() / 2., this.getHeight() / 2.);
  }

  private void drawBackgroundGrid(Graphics2D g) {
    this.setBackground(Color.LIGHT_GRAY);

    int width = this.getWidth();
    int height = this.getHeight();
    GridDimension dimension = model.getDimension();

    g.setColor(Color.DARK_GRAY);
    for (int col = 0; col < dimension.cols(); col++) {
      int x = col * width / dimension.cols();
      g.drawLine(x, 0, x, height);
    }
    for (int row = 0; row < dimension.rows(); row++) {
      int y = row * height / dimension.cols();
      g.drawLine(0, y, width, y);
    }
  }


  private void coverHiddenCells(Graphics2D g) {
    Rectangle2D box = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
    GridDimension dimension = model.getDimension();
    CellPositionToPixelConverter backgroundConverter = new CellPositionToPixelConverter(
        box, dimension, 0
    );
    CellPositionToPixelConverter cellConverter = new CellPositionToPixelConverter(
        box, dimension, RIDGE_WIDTH
    );

    for (GridCell<IMinesweeperCell> gc : model.getHiddenCells()) {
      // coloured ridges are actually triangles with a smaller rectangle on top
      Rectangle2D boundingCell = backgroundConverter.getBoundsForCell(gc.pos());
      g.setColor(Color.DARK_GRAY);
      g.fill(boundingCell);

      Path2D triangle = new Path2D.Double();
      triangle.moveTo(boundingCell.getX(), boundingCell.getY());
      triangle.lineTo(boundingCell.getMaxX(), boundingCell.getY());
      triangle.lineTo(boundingCell.getX(), boundingCell.getMaxY());
      triangle.closePath();

      g.setColor(Color.LIGHT_GRAY);
      g.fill(triangle);

      Rectangle2D onTop = cellConverter.getBoundsForCell(gc.pos());
      g.setColor(Color.GRAY);
      g.fill(onTop);

      // drawing flags
      if (gc.elem().isFlagged()) {
        drawImage(g, backgroundConverter.getBoundsForCell(gc.pos()), "flag.png");
      }
    }
  }

  private void drawGameBoard(Graphics2D g) {
    Rectangle2D box = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
    CellPositionToPixelConverter posToPix = new CellPositionToPixelConverter(
        box, model.getDimension(), 2
    );

    for (GridCell<IMinesweeperCell> gc : model.getMinefield()) {
      if (gc.elem().isMine()) {
        drawImage(g, posToPix.getBoundsForCell(gc.pos()), "mine.png");
      } else if (gc.elem().getAdjacencyCount() > 0) {
        drawImage(g, posToPix.getBoundsForCell(gc.pos()), gc.elem().getAdjacencyCount() + ".png");
      }
    }
  }

  private void drawImage(Graphics2D g, Rectangle2D boundingBox, String fileName) {
    try {
      InputStream stream = View.class.getResourceAsStream(fileName);
      if (stream == null) {
        throw new RuntimeException("error while loading file");
      }

      Image image = ImageIO.read(stream);
      int x = (int) boundingBox.getX();
      int y = (int) boundingBox.getY();
      int w = (int) boundingBox.getWidth();
      int h = (int) boundingBox.getHeight();
      Color transparent = new Color(0, 0, 0, 0);

      g.drawImage(image, x, y, w, h, transparent, this);

      stream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
