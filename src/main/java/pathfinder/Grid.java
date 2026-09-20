package pathfinder;

import lombok.Getter;
import lombok.Setter;
import pathfinder.model.Cell;
import pathfinder.model.CellState;

/**
 * Construct that holds information about the grid
 * Number of columns and rows, state of cells, etc.
 */
@Getter
@Setter
public class Grid {
    private int rows;
    private int cols;
    private Cell[][] cells;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        cells = new Cell[rows][cols];
        reset();
    }

    /**
     * Resets the whole grid.
     * New initialization of every cell in state 'Empty'.
     */
    public void reset(){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j, CellState.Empty);
            }
        }
    }

    /**
     * Fills all the cells with desired cell state.
     *
     * @param state Desired state for all cells
     */
    public void fill(CellState state){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].setState(state);
            }
        }
    }
}
