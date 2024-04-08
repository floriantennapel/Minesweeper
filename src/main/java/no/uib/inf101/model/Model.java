package no.uib.inf101.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Model {
  private static final String MAP_FILE = "map.txt";

  private final List<MovableEntity> entities;
  private final int[][] map;

  private final int rows;
  private final int cols;

  public Model() {
    entities = new ArrayList<>();
    entities.add(new MovableEntity(new Vector2D(4.5, 1.5)));
    map = readMapFromFile(MAP_FILE);
    rows = map.length;
    cols = map[0].length;
  }



  public MovableEntity getPlayer() {
    return entities.get(0);
  }

  private int[][] readMapFromFile(String mapFile) {
    try {
      InputStream stream = Model.class.getResourceAsStream(mapFile);
      if (stream == null) {
        throw new RuntimeException("Could not find " + mapFile);
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      String[] dimension = reader.readLine().split(",");
      int rows = Integer.parseInt(dimension[0]);
      int cols = Integer.parseInt(dimension[1]);

      int[][] map = new int[rows][cols];
      for (int i = 0; i < rows; i++) {
        String line = reader.readLine();
        for (int j = 0; j < cols; j++) {
          map[i][j] = line.charAt(j) - '0';
        }
      }

      return map;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }
}
