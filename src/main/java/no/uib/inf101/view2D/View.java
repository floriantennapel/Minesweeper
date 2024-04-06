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
  }

  private void drawBackground(Graphics2D g) {
    this.setBackground(Color.LIGHT_GRAY);
    int[] dimension = model.getDimension();
    int width = this.getWidth();
    int height = this.getHeight();

    double gridHeight = height / (double) dimension[0];
    double gridWidth = width / (double) dimension[1];
    g.setColor(Color.BLACK);

    for (int i = 0; i < dimension[0]; i++) {
      int y = (int) (i * gridHeight);
      g.drawLine(0, y, width, y);
    }
    for (int i = 0; i < dimension[1]; i++) {
      int x = (int) (i * gridWidth);
      g.drawLine(x, 0, x, height);
    }
  }

  private void drawPlayer(Graphics2D g) {
    MovableEntity player = model.getPlayer();

    Vector2D pos = gridPositionToPixel(player.getPosition());
    Vector2D dirEnd = gridPositionToPixel(
        player.getPosition().add(player.getDirection())
    );
    int playerSize = 10;
    int x = (int) pos.x() - playerSize / 2;
    int y = (int) pos.y() - playerSize / 2;

    g.setColor(Color.RED);
    g.fillOval(x, y, playerSize, playerSize);
    Line2D line = new Line2D.Double(pos.x(), pos.y(), dirEnd.x(), dirEnd.y());
    g.draw(line);
  }

  private Vector2D gridPositionToPixel(Vector2D v) {
    int[] dimension = model.getDimension();
    int rows = dimension[0];
    int cols = dimension[1];

    double x = this.getWidth() * v.x() / cols;
    double y = this.getHeight() * v.y() / rows;

    return new Vector2D(x, y);
  }
}
