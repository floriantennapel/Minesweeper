package no.uib.inf101;

import no.uib.inf101.controller.MouseController;
import no.uib.inf101.model.Model;
import no.uib.inf101.view.View;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    Model model = new Model(16, 16, 40);
    View view = new View(model);
    new MouseController(model, view);

    JFrame frame = new JFrame("Minesweeper");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
  }
}
