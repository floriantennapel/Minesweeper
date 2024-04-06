package no.uib.inf101;

import no.uib.inf101.controller.KeyController;
import no.uib.inf101.model.Model;
import no.uib.inf101.view2D.View;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    Model model = new Model();
    View view = new View(model);
    new KeyController(model, view);

    JFrame frame = new JFrame();
    frame.setTitle("INF101");
    frame.setContentPane(view);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
