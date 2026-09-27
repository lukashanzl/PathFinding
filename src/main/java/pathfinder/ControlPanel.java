package pathfinder;

import pathfinder.model.GeneratorType;
import pathfinder.model.PathfinderType;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ControlPanel extends JPanel {

    private static final Color HEADER_COLOR = new Color(0x333333);
    private static final Color DIVIDER_COLOR = new Color(0xCCCCCC);

    public ControlPanel(Consumer<GeneratorType> onGenerate,
                        Consumer<PathfinderType> onSolve) {   // <-- no Grid, no GridPanel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(210, 100));

        // subtle divider on the right edge + inner padding
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, DIVIDER_COLOR),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        add(makeHeader("Maze Generation"));
        add(Box.createVerticalStrut(8));
        add(makeButton("Depth-First", () -> onGenerate.accept(GeneratorType.DEPTH_FIRST)));
        // add more generator buttons here — same makeButton(...) call

        add(Box.createVerticalStrut(24));

        add(makeHeader("Pathfinders"));
        add(Box.createVerticalStrut(8));
        add(makeButton("A* Pathfinder", () -> onSolve.accept(PathfinderType.A_STAR)));

        add(Box.createVerticalGlue());   // keep everything pinned to the top
    }

    private JLabel makeHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(HEADER_COLOR);
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

    private JButton makeButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));   // full width, fixed height
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());
        return button;
    }
}
