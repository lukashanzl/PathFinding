package pathfinder.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AStarCell extends Cell {

    private double g;
    private double h;
    private double f;
    private AStarCell parent;

    public AStarCell(int row, int col, CellState state) {
        super(row, col, state);
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }
}
