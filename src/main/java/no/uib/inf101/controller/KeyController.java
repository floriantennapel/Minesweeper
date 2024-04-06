package no.uib.inf101.controller;

import no.uib.inf101.model.Direction;
import no.uib.inf101.model.Model;
import no.uib.inf101.model.MovableEntity;
import no.uib.inf101.view2D.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyController implements KeyListener, ActionListener {
  private final Model model;
  private final View view;
  private final Timer timer;
  private final Map<Integer, Boolean> keysPressed;

  public KeyController(Model model, View view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("arguments cannot be null");
    }

    this.model = model;
    this.view = view;

    int fps = 30;
    this.timer = new Timer(1000/fps, this);
    timer.start();

    view.setFocusable(true);
    view.addKeyListener(this);

    keysPressed = new HashMap<>(Map.of(
        KeyEvent.VK_W, false,
        KeyEvent.VK_A, false,
        KeyEvent.VK_S, false,
        KeyEvent.VK_D, false,
        KeyEvent.VK_LEFT, false,
        KeyEvent.VK_RIGHT, false,
        KeyEvent.VK_SPACE, false
    ));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    MovableEntity player = model.getPlayer();
    double moveScalar = 0.1;
    double sqrt2 = 1.4142;
    double diagonalScalar = moveScalar / sqrt2;

    // diagonal movement should not be faster
    double moveAmount = moveScalar;
    if ((keysPressed.get(KeyEvent.VK_W) || keysPressed.get(KeyEvent.VK_S)) &&
        (keysPressed.get(KeyEvent.VK_A) || keysPressed.get(KeyEvent.VK_D))) {
        moveAmount = diagonalScalar;
    }

    for (int keycode : keysPressed.keySet()) {
      if (keysPressed.get(keycode)) {
        switch (keycode) {
          case KeyEvent.VK_W -> player.move(Direction.FORWARD, moveAmount);
          case KeyEvent.VK_A -> player.move(Direction.LEFT, moveAmount);
          case KeyEvent.VK_S -> player.move(Direction.BACKWARD, moveAmount);
          case KeyEvent.VK_D -> player.move(Direction.RIGHT, moveAmount);
          case KeyEvent.VK_LEFT -> player.rotate(false, moveScalar);
          case KeyEvent.VK_RIGHT -> player.rotate(true, moveScalar);
        }
      }
    }

    view.repaint();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (keysPressed.containsKey(e.getKeyCode())) {
      keysPressed.put(e.getKeyCode(), true);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (keysPressed.containsKey(e.getKeyCode())) {
      keysPressed.put(e.getKeyCode(), false);
    }
  }



  // unused method
  @Override
  public void keyTyped(KeyEvent e) {}
}
