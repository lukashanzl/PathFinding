import pathfinder.Grid;
import pathfinder.GridPanel;
import pathfinder.model.CellState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame {

    private static final Integer WIDTH = 800;
    private static final Integer HEIGHT = 600;

    static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }

    public Application() {
        super("PathFinder - Grid Visualization");

        int rows = 40;
        int cols = 40;

        GridPanel gPanel = new GridPanel(HEIGHT, WIDTH, rows, cols);

        add(gPanel);
        pack();

        setVisible(true);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                dispose(); System.exit(0);
            }
        });
    }
}
