package pathfinder.service.maze;

import pathfinder.Grid;
import pathfinder.model.Cell;
import pathfinder.model.CellState;
import pathfinder.model.GeneratorType;

import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator {

    Random random = new Random();

    public void generate(Grid grid, GeneratorType type){
        switch (type){
            case DEPTH_FIRST -> depthFirst(grid);
            default -> throw new IllegalArgumentException("Generator Type is not defined");
        }
    }

    /// Uses a depth-first Algorithm to generate a Maze
    private void depthFirst(Grid grid){
        grid.fill(CellState.Wall);

        checkNeighbors(grid, grid.getCells()[0][0]);

        grid.getCells()[0][0].setState(CellState.Start);
        grid.getCells()[grid.getRows()-2][grid.getCols()-2].setState(CellState.End);
    }

    public void checkNeighbors(Grid grid, Cell cell){
        cell.setVisited(true);

        System.out.printf("Currently checking cell: Row=%d, Col=%d %n", cell.getRow(), cell.getCol());

        ArrayList<Cell> neighbors = createNeighbors(grid, cell);

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

    private static ArrayList<Cell> createNeighbors(Grid grid, Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<>();

        Cell neighbor;
        if(cell.getRow()+2 < grid.getRows()){
            neighbor = grid.getCells()[cell.getRow()+2][cell.getCol()];
            neighbors.add(neighbor);
        }
        if(cell.getRow()-2 >= 0){
            neighbor = grid.getCells()[cell.getRow()-2][cell.getCol()];
            neighbors.add(neighbor);
        }
        if(cell.getCol()+2 < grid.getCols()){
            neighbor = grid.getCells()[cell.getRow()][cell.getCol()+2];
            neighbors.add(neighbor);
        }
        if(cell.getCol()-2 >= 0){
            neighbor = grid.getCells()[cell.getRow()][cell.getCol()-2];
            neighbors.add(neighbor);
        }
        return neighbors;
    }
}
