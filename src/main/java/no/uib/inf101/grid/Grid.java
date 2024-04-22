package no.uib.inf101.grid;

import java.util.ArrayList;
import java.util.List;

public class Grid<T> implements IGrid<T> {

	private final List<List<T>> data;
	private final int cols;
	private final int rows;

	public Grid(int rows, int cols, T defaultElem) {
	    this.rows = rows;
	    this.cols = cols;
	    this.data = new ArrayList<>(rows);
	    
	    for (int i = 0; i < rows; i++) {
	      List<T> row = new ArrayList<>(cols);
	      for (int j = 0; j < cols; j++) {
	        row.add(defaultElem);
	      }
	      this.data.add(row);
	    }
	  }

	public Grid(int rows, int cols) {
	    this(rows, cols, null);
	  }

	@Override
	public int rows() {
		return rows;
	}

	@Override
	public int cols() {
		return cols;
	}

	@Override
	public List<GridCell<T>> getCells() {
		ArrayList<GridCell<T>> cells = new ArrayList<>();

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				CellPosition pos = new CellPosition(row, col);
				cells.add(new GridCell<>(pos, this.get(pos)));
			}
		}

		return cells;
	}

	@Override
	public T get(CellPosition pos) {
		return this.data.get(pos.row()).get(pos.col());
	}

	@Override
	public void set(CellPosition pos, T elem) {
		this.data.get(pos.row()).set(pos.col(), elem);
	}
}
