package pathfinder;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class GridPanel extends JPanel {

    private Grid grid;
    private int height;
    private int width;

    public GridPanel(int height, int width, int rows, int cols) {
        this.grid = new Grid(rows, cols);
        this.height = height;
        this.width = width;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int k;

        int htOfRow = height / grid.getRows();
        for(k=0; k<grid.getRows(); k++){
            g.drawLine(0, k * htOfRow, width, k * htOfRow);
        }

        int wdOfRow = width / grid.getCols();
        for(k=0; k<grid.getCols(); k++){
            g.drawLine(k * wdOfRow, 0, k * wdOfRow, height);
        }
    }
}
