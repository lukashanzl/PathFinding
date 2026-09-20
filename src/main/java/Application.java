import pathfinder.Grid;
import pathfinder.GridPanel;
import pathfinder.model.Cell;
import pathfinder.model.CellState;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class Application extends JFrame {

    private final int ROWS = 40;
    private final int COLS = 40;

    static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }

    public Application() {
        super("PathFinder - Grid Visualization");

        GridPanel gPanel = new GridPanel(ROWS, COLS);

        add(gPanel);
        pack();

        generateMaze(gPanel);
        gPanel.repaint();

        setVisible(true);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                dispose(); System.exit(0);
            }
        });
    }

    /// Uses a depth-first Algorithm to generate a Maze
    private void generateMaze(GridPanel panel){
        panel.getGrid().fill(CellState.Wall);

        Cell[][] cells = panel.getGrid().getCells();

        cells[0][0].setState(CellState.Start);
        cells[ROWS-2][COLS-2].setState(CellState.End);

        panel.setGrid(checkNeighbors(
                panel.getGrid(),
                panel.getGrid().getCells()[0][0]
        ));

        panel.getGrid().getCells()[0][0].setState(CellState.Start);
        panel.getGrid().getCells()[ROWS-2][COLS-2].setState(CellState.End);
    }

    public Grid checkNeighbors(Grid grid, Cell cell){
        cell.setVisited(true);

        System.out.printf("Currently checking cell: Row=%d, Col=%d %n", cell.getRow(), cell.getCol());

        ArrayList<Cell> neighbors = new ArrayList<Cell>();

        Cell neighbor;
        if(cell.getRow()+2 < ROWS){
            neighbor = grid.getCells()[cell.getRow()+2][cell.getCol()];
            System.out.printf("Adding neighbor cell: Row=%d, Col=%d %n", neighbor.getRow(), neighbor.getCol());
            neighbors.add(neighbor);
        }
        if(cell.getRow()-2 >= 0){
            neighbor = grid.getCells()[cell.getRow()-2][cell.getCol()];
            System.out.printf("Adding neighbor cell: Row=%d, Col=%d %n", neighbor.getRow(), neighbor.getCol());
            neighbors.add(neighbor);
        }
        if(cell.getCol()+2 < COLS){
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
