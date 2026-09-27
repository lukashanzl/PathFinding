package pathfinder.service.maze.types;

import pathfinder.Grid;
import pathfinder.model.Cell;
import pathfinder.model.CellState;
import pathfinder.service.maze.interfaces.MazeStrategy;
import pathfinder.service.utils.ArrayListUtils;

import java.util.ArrayList;
import java.util.Random;

public class DepthFirst implements MazeStrategy {

    Random random = new Random();

    @Override
    public void generate(Grid grid){
        grid.fill(CellState.Wall);
        grid.resetVisited();

        checkNeighbors(grid, grid.getCells()[0][0]);

        grid.getCells()[0][0].setState(CellState.Start);
        grid.getCells()[grid.getRows()-2][grid.getCols()-2].setState(CellState.End);
    }

    private void checkNeighbors(Grid grid, Cell cell){
        cell.setVisited(true);

        ArrayList<Cell> neighbors = ArrayListUtils.createNeighbors(grid, cell,2);

        while(!neighbors.isEmpty()) {
            int randomIdx = random.nextInt(neighbors.size());

            Cell cellToCheck = neighbors.get(randomIdx);

            if(!cellToCheck.isVisited()){
                // neighbors are 2 apart: (row±2, col) and (row, col±2)
                // when you pick an unvisited neighbor `next` from `cell`:
                int wallRow = (cell.getRow() + cellToCheck.getRow()) / 2;   // midpoint
                int wallCol = (cell.getCol() + cellToCheck.getCol()) / 2;

                grid.getCells()[wallRow][wallCol].setState(CellState.Empty); // knock out the wall between
                cellToCheck.setState(CellState.Empty);                       // carve the room

                checkNeighbors(grid, cellToCheck);
            }
            neighbors.remove(neighbors.get(randomIdx));
        }
    }
}
