import pathfinder.GridPanel;
import pathfinder.model.GeneratorType;
import pathfinder.service.maze.MazeGenerator;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

        MazeGenerator mazeGenerator = new MazeGenerator();
        mazeGenerator.generate(gPanel.getGrid(), GeneratorType.DEPTH_FIRST);
        gPanel.repaint();

        setVisible(true);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                dispose(); System.exit(0);
            }
        });
    }
}
