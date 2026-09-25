package pathfinder.service.utils;

import pathfinder.Grid;
import pathfinder.model.AStarCell;
import pathfinder.model.Cell;

import java.util.ArrayList;

public final class ArrayListUtils {
    private ArrayListUtils(){}

    public static ArrayList<Cell> createNeighbors(Grid grid, Cell cell, int range) {
        ArrayList<Cell> neighbors = new ArrayList<>();

        Cell neighbor;
        if(cell.getRow()+range < grid.getRows()){
            neighbor = grid.getCells()[cell.getRow()+range][cell.getCol()];
            neighbors.add(neighbor);
        }
        if(cell.getRow()-range >= 0){
            neighbor = grid.getCells()[cell.getRow()-range][cell.getCol()];
            neighbors.add(neighbor);
        }
        if(cell.getCol()+range < grid.getCols()){
            neighbor = grid.getCells()[cell.getRow()][cell.getCol()+range];
            neighbors.add(neighbor);
        }
        if(cell.getCol()-range >= 0){
            neighbor = grid.getCells()[cell.getRow()][cell.getCol()-range];
            neighbors.add(neighbor);
        }
        return neighbors;
    }

    public static ArrayList<AStarCell> cellListToAStarList(ArrayList<Cell> cellList){
        ArrayList<AStarCell> retList = new ArrayList<>();
        for (Cell cell : cellList){
            retList.add(new AStarCell(cell.getRow(), cell.getCol(), cell.getState()));
        }

        return retList;
    }
}
