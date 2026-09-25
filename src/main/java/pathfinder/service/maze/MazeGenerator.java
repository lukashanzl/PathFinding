package pathfinder.service.maze;

import pathfinder.Grid;
import pathfinder.model.GeneratorType;
import pathfinder.service.maze.interfaces.MazeStrategy;
import pathfinder.service.maze.types.DepthFirst;

import java.util.EnumMap;
import java.util.Map;

public class MazeGenerator {

    private final Map<GeneratorType, MazeStrategy> strategies = new EnumMap<>(GeneratorType.class);

    public MazeGenerator(){
        strategies.put(GeneratorType.DEPTH_FIRST, new DepthFirst());
    }

    public void generate(Grid grid, GeneratorType type){
        MazeStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No generator for: " + type);
        }
        strategy.generate(grid);
    }
}
