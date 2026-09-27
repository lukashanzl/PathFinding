package pathfinder.service.pathfinder.types;

import pathfinder.Grid;
import pathfinder.model.*;
import pathfinder.service.pathfinder.interfaces.PathfinderStrategy;
import pathfinder.service.utils.ArrayListUtils;

import java.util.*;

public class AStar implements PathfinderStrategy {

    private ArrayList<AStarCell> openList, closedList;
    private int startRow, startCol, endRow, endCol;
    private Grid grid;

    public void init(Grid grid, PathfinderType type){
        this.openList = new ArrayList<>();
        this.closedList = new ArrayList<>();
        this.grid = grid;

        for (int i = 0; i < this.grid.getCells().length; i++) {
            for (int j = 0; j < this.grid.getCells()[i].length; j++) {
                if (this.grid.getCells()[i][j].getState() == CellState.Start){
                    startRow = this.grid.getCells()[i][j].getRow();
                    startCol = this.grid.getCells()[i][j].getCol();
                }
                if (this.grid.getCells()[i][j].getState() == CellState.End){
                    endRow = this.grid.getCells()[i][j].getRow();
                    endCol = this.grid.getCells()[i][j].getCol();
                }
            }
        }

        openList.add(new AStarCell(
                startRow,
                startCol,
                grid.getCells()[startRow][startCol].getState()
        ));
        calculateValues(openList.getFirst(), openList.getFirst(), endRow, endCol);
    }

    @Override
    public void run(Grid grid) {

        for (int i = 0; i < grid.getCells().length; i++) {
            for (int j = 0; j < grid.getCells()[i].length; j++) {
                if (grid.getCells()[i][j].getState() == CellState.Start){
                    startRow = grid.getCells()[i][j].getRow();
                    startCol = grid.getCells()[i][j].getCol();
                }
                if (grid.getCells()[i][j].getState() == CellState.End){
                    endRow = grid.getCells()[i][j].getRow();
                    endCol = grid.getCells()[i][j].getCol();
                }
            }
        }

        openList = new ArrayList<>();
        closedList = new ArrayList<>();

        openList.add(new AStarCell(
                startRow,
                startCol,
                grid.getCells()[startRow][startCol].getState()
        ));
        calculateValues(openList.getFirst(), openList.getFirst(), endRow, endCol);

        while(!openList.isEmpty()){
            // Look for lowest f on openList move to closedList
            AStarCell currentCell = openList.stream()
                    .min(Comparator.comparing(AStarCell::getF))
                    .orElseThrow(NoSuchElementException::new);

            if (currentCell.getState() == CellState.End){
                // Finish found
                closedList.add(currentCell);
                break;
            }

            // create neighbors
            ArrayList<AStarCell> neighbors;
            neighbors = ArrayListUtils.cellListToAStarList(ArrayListUtils.createNeighbors(grid, currentCell, 1));

            openList.remove(currentCell);

            // Do the A* calculations
            for (AStarCell cell : neighbors){

                boolean inClosed = closedList.stream()
                        .anyMatch(item -> item.getRow() == cell.getRow() && item.getCol() == cell.getCol());
                boolean inOpen = openList.stream()
                        .anyMatch(item -> item.getRow() == cell.getRow() && item.getCol() == cell.getCol());

                if(inClosed) continue;

                if (cell.getState() == CellState.Wall){
                    closedList.add(cell);
                    continue;
                }

                calculateValues(cell, currentCell, endRow, endCol);

                if (inOpen){
                    if (cell.getG() > currentCell.getG()) continue;
                }

                cell.setParent(currentCell);
                openList.add(cell);
            }
        }

        AStarCell end = closedList.stream()
                .filter(cell -> cell.getState() == CellState.End)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        AStarCell next = setShortestPath(grid, end);

        while (next.getParent() != null){
            next = setShortestPath(grid, next);
        }
    }

    public SearchStatus step(){
        // Look for lowest f on openList move to closedList
        AStarCell currentCell = openList.stream()
                .min(Comparator.comparing(AStarCell::getF))
                .orElseThrow(NoSuchElementException::new);

        if (currentCell.getState() == CellState.End){
            // Finish found
            closedList.add(currentCell);
            return SearchStatus.FOUND;
        }

        // create neighbors
        ArrayList<AStarCell> neighbors;
        neighbors = ArrayListUtils.cellListToAStarList(ArrayListUtils.createNeighbors(grid, currentCell, 1));

        openList.remove(currentCell);

        // Do the A* calculations
        for (AStarCell cell : neighbors){

            boolean inClosed = closedList.stream()
                    .anyMatch(item -> item.getRow() == cell.getRow() && item.getCol() == cell.getCol());
            boolean inOpen = openList.stream()
                    .anyMatch(item -> item.getRow() == cell.getRow() && item.getCol() == cell.getCol());

            if(inClosed) continue;

            if (cell.getState() == CellState.Wall){
                closedList.add(cell);
                continue;
            }

            calculateValues(cell, currentCell, endRow, endCol);

            if (inOpen){
                if (cell.getG() > currentCell.getG()) continue;
            }

            cell.setParent(currentCell);

            Cell gridCell = grid.getCells()[cell.getRow()][cell.getCol()];
            if (gridCell.getState() != CellState.Start && gridCell.getState() != CellState.End) {
                gridCell.setState(CellState.Visited);
            }

            openList.add(cell);
        }

        if (openList.isEmpty()){
            return SearchStatus.NO_PATH;
        } else {
            return SearchStatus.RUNNING;
        }
    }

    private void calculateValues(AStarCell cell, AStarCell source, int endRow, int endCol){
            cell.setG(source.getG() + 1);
            int dRow = endRow - cell.getRow();
            int dCol = endCol - cell.getCol();
            cell.setH(Math.abs(dRow) + Math.abs(dCol));
            cell.setF(cell.getG() + cell.getH());
    }

    private AStarCell setShortestPath(Grid grid, AStarCell cell){
        if (cell.getState() != CellState.End && cell.getState() != CellState.Start){
            grid.getCells()[cell.getRow()][cell.getCol()].setState(CellState.Path);
        }
        return cell.getParent();
    }

    public void reconstructPath(){
        AStarCell end = closedList.stream()
                .filter(cell -> cell.getState() == CellState.End)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        AStarCell next = setShortestPath(grid, end);

        while (next.getParent() != null){
            next = setShortestPath(grid, next);
        }
    }
}


