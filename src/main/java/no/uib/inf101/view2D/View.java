package no.uib.inf101.view2D;

import no.uib.inf101.model.Model;
import no.uib.inf101.model.MovableEntity;
import no.uib.inf101.model.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class View extends JPanel {
  private final Model model;

  public View(Model model) {
    this.model = model;
    this.setPreferredSize(new Dimension(400, 400));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    drawBackground(g2);
    drawPlayer(g2);
    castRays(g2);
  }

  private void castRays(Graphics2D g) {
    g.setColor(Color.YELLOW);

    MovableEntity player = model.getPlayer();
    Vector2D pos = player.getPosition();
    Vector2D direction = player.getDirection();
    Vector2D viewport = player.getViewport();
    Vector2D screenPos = gridPositionToPixel(player.getPosition());

    int numberOfRays = this.getWidth();
    for (int i = 0; i <= numberOfRays; i++) {
      double scaleFactor = 2 * i / (double) numberOfRays - 1;
      Vector2D ray = gridPositionToPixel(
          pos.add(direction).add(viewport.scaled(scaleFactor))
      );
      Line2D line = new Line2D.Double(
          screenPos.x(), screenPos.y(),
          ray.x(), ray.y()
      );
      g.draw(line);
    }
  }

  private void drawBackground(Graphics2D g) {
    this.setBackground(Color.LIGHT_GRAY);
    int rows = model.getRows();
    int cols = model.getCols();
    int width = this.getWidth();
    int height = this.getHeight();

    double gridHeight = height / (double) rows;
    double gridWidth = width / (double) cols;
    g.setColor(Color.BLACK);

    for (int i = 0; i < rows; i++) {
      int y = (int) (i * gridHeight);
      g.drawLine(0, y, width, y);
    }
    for (int i = 0; i < cols; i++) {
      int x = (int) (i * gridWidth);
      g.drawLine(x, 0, x, height);
    }
  }

  private void drawPlayer(Graphics2D g) {
    MovableEntity player = model.getPlayer();

    Vector2D pos = gridPositionToPixel(player.getPosition());
    Vector2D endDirection = player.getPosition().add(player.getDirection());
    Vector2D dirEnd = gridPositionToPixel(endDirection);
    Vector2D viewStart = gridPositionToPixel(endDirection.add(player.getViewport().scaled(-1.0)));
    Vector2D viewEnd = gridPositionToPixel(endDirection.add(player.getViewport()));

    int playerSize = 10;
    int x = (int) pos.x() - playerSize / 2;
    int y = (int) pos.y() - playerSize / 2;

    g.setColor(Color.RED);
    g.fillOval(x, y, playerSize, playerSize);
    Line2D line = new Line2D.Double(pos.x(), pos.y(), dirEnd.x(), dirEnd.y());
    g.draw(line);
    line = new Line2D.Double(viewStart.x(), viewStart.y(), viewEnd.x(), viewEnd.y());
    g.draw(line);
  }

  private Vector2D gridPositionToPixel(Vector2D v) {
    int rows = model.getRows();
    int cols = model.getCols();

    double x = this.getWidth() * v.x() / cols;
    double y = this.getHeight() * v.y() / rows;

    return new Vector2D(x, y);
  }
}
