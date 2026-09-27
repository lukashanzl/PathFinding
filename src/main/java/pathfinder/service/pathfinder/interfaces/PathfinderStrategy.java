package pathfinder.service.pathfinder.interfaces;

import pathfinder.Grid;
import pathfinder.model.PathfinderType;
import pathfinder.model.SearchStatus;

public interface PathfinderStrategy {
    void run(Grid grid);

    SearchStatus step();

    void init(Grid grid, PathfinderType type);

    void reconstructPath();
}
