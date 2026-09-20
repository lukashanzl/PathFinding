package pathfinder;

import lombok.Getter;
import lombok.Setter;
import pathfinder.model.Cell;
import pathfinder.model.CellState;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class GridPanel extends JPanel {

    private static final int CELL_PIXEL = 20;

    private Grid grid;

    public GridPanel(int rows, int cols) {
        this.grid = new Grid(rows, cols);
        setPreferredSize(new Dimension(cols * CELL_PIXEL, rows * CELL_PIXEL));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Cell[][] cells = grid.getCells();
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                Cell cell = cells[row][col];
                g.setColor(cell.getState().getColor());
                g.fillRect(col * CELL_PIXEL, row * CELL_PIXEL, CELL_PIXEL, CELL_PIXEL);
            }
        }

        // set grid over it
        g.setColor(new Color(200, 200, 200));
        int w = grid.getCols() * CELL_PIXEL, h = grid.getRows() * CELL_PIXEL;
        for (int row = 0; row <= grid.getRows(); row++) g.drawLine(0, row * CELL_PIXEL, w, row * CELL_PIXEL);
        for (int col = 0; col <= grid.getCols(); col++) g.drawLine(col * CELL_PIXEL, 0, col * CELL_PIXEL, h);
    }
}
