import pathfinder.ControlPanel;
import pathfinder.GridPanel;
import pathfinder.model.SearchStatus;
import pathfinder.service.maze.MazeGenerator;
import pathfinder.service.pathfinder.Pathfinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame {

    private final int ROWS = 30;
    private final int COLS = 30;

    static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }

    public Application() {
        super("PathFinder - Grid Visualization");

        GridPanel mazePanel = new GridPanel(ROWS, COLS);
        ControlPanel controlPanel = getControlPanel(mazePanel);

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

    private static ControlPanel getControlPanel(GridPanel mazePanel) {
        MazeGenerator generator = new MazeGenerator();
        Pathfinder pathfinder = new Pathfinder();

        return new ControlPanel(
                generatorType -> {
                    generator.generate(mazePanel.getGrid(), generatorType);
                    mazePanel.repaint();
                    },
                pathfinderType -> {
                    pathfinder.init(mazePanel.getGrid(), pathfinderType);
                    Timer timer = new Timer(30, null);
                    timer.addActionListener(e -> {
                        SearchStatus status = pathfinder.step(pathfinderType);
                        mazePanel.repaint();
                        if (status != SearchStatus.RUNNING){
                            timer.stop();
                            if (status == SearchStatus.FOUND){
                                pathfinder.reconstructPath(pathfinderType);
                                mazePanel.repaint();
                            }
                        }
                    });
                    timer.start();
                }
        );
    }
}
