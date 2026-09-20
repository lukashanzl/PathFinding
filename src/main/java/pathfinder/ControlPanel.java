package pathfinder;

import pathfinder.model.GeneratorType;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.function.Consumer;

public class ControlPanel extends JPanel {

    private Grid grid;

    public ControlPanel(Consumer<GeneratorType> onGenerate) {   // <-- no Grid, no GridPanel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel mazeHeader = new JLabel("Maze Generation");
        mazeHeader.setFont(new Font("Serif", Font.BOLD, 18));
        add(mazeHeader);

        JButton depthFirst = new JButton("Depth-First");
        depthFirst.addActionListener(e -> onGenerate.accept(GeneratorType.DEPTH_FIRST));
        add(depthFirst);
        // add more generator buttons later — they all call onGenerate with their own type


        JLabel pathfinderHeader = new JLabel("Pathfinders");
        pathfinderHeader.setFont(new Font("Serif", Font.BOLD, 18));
        add(pathfinderHeader);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(getWidth(), 0, getWidth(), getHeight());
    }
}
