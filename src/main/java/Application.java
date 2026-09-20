import pathfinder.ControlPanel;
import pathfinder.GridPanel;
import pathfinder.model.GeneratorType;
import pathfinder.service.maze.MazeGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.classfile.Signature;

public class Application extends JFrame {

    private final int ROWS = 40;
    private final int COLS = 40;

    static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }

    public Application() {
        super("PathFinder - Grid Visualization");

        GridPanel mazePanel = new GridPanel(ROWS, COLS);
        MazeGenerator generator = new MazeGenerator();

        ControlPanel controlPanel = new ControlPanel(type -> {
            generator.generate(mazePanel.getGrid(), type);   // mutate the model
            mazePanel.repaint();                              // refresh the view
        });

        add(mazePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.WEST);
        pack();

        setVisible(true);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                dispose(); System.exit(0);
            }
        });
    }
}
