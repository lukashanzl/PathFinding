package pathfinder.service.maze;

import pathfinder.Grid;
import pathfinder.GridPanel;
import pathfinder.model.Cell;
import pathfinder.model.CellState;
import pathfinder.model.GeneratorType;

import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator {

    public void generate(GridPanel panel, GeneratorType type){
        switch (type){
            case DEPTH_FIRST -> depthFirst(panel);
        }
    }

    /// Uses a depth-first Algorithm to generate a Maze
    private void depthFirst(GridPanel panel){
        panel.getGrid().fill(CellState.Wall);

        panel.setGrid(checkNeighbors(
                panel.getGrid(),
                panel.getGrid().getCells()[0][0]
        ));

        panel.getGrid().getCells()[0][0].setState(CellState.Start);
        panel.getGrid().getCells()[panel.getGrid().getRows()-2][panel.getGrid().getCols()-2].setState(CellState.End);
    }

    public Grid checkNeighbors(Grid grid, Cell cell){
        cell.setVisited(true);

        System.out.printf("Currently checking cell: Row=%d, Col=%d %n", cell.getRow(), cell.getCol());

        ArrayList<Cell> neighbors = new ArrayList<>();

        Cell neighbor;
        if(cell.getRow()+2 < grid.getRows()){
            neighbor = grid.getCells()[cell.getRow()+2][cell.getCol()];
            System.out.printf("Adding neighbor cell: Row=%d, Col=%d %n", neighbor.getRow(), neighbor.getCol());
            neighbors.add(neighbor);
        }
        if(cell.getRow()-2 >= 0){
            neighbor = grid.getCells()[cell.getRow()-2][cell.getCol()];
            System.out.printf("Adding neighbor cell: Row=%d, Col=%d %n", neighbor.getRow(), neighbor.getCol());
            neighbors.add(neighbor);
        }
        if(cell.getCol()+2 < grid.getCols()){
            neighbor = grid.getCells()[cell.getRow()][cell.getCol()+2];
            System.out.printf("Adding neighbor cell: Row=%d, Col=%d %n", neighbor.getRow(), neighbor.getCol());
            neighbors.add(neighbor);
        }
        if(cell.getCol()-2 >= 0){
            neighbor = grid.getCells()[cell.getRow()][cell.getCol()-2];
            System.out.printf("Adding neighbor cell: Row=%d, Col=%d %n", neighbor.getRow(), neighbor.getCol());
            neighbors.add(neighbor);
        }

        Random random = new Random();

        while(!neighbors.isEmpty()) {
            int randomIdx = random.nextInt(neighbors.size());

            Cell cellToCheck = neighbors.get(randomIdx);

            System.out.printf("Selected neighbor: Row=%d, Col=%d, visited=%b %n", cellToCheck.getRow(), cellToCheck.getCol(), cellToCheck.isVisited());

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

        return grid;
    }
}
