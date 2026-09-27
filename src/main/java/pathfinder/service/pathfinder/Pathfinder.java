package pathfinder.service.pathfinder;

import pathfinder.Grid;
import pathfinder.model.PathfinderType;
import pathfinder.model.SearchStatus;
import pathfinder.service.pathfinder.interfaces.PathfinderStrategy;
import pathfinder.service.pathfinder.types.AStar;

import java.util.EnumMap;
import java.util.Map;

public class Pathfinder {
    private final Map<PathfinderType, PathfinderStrategy> strategies = new EnumMap<>(PathfinderType.class);

    public Pathfinder(){
        strategies.put(PathfinderType.A_STAR, new AStar());
    }

    public void solve(Grid grid, PathfinderType type){
        PathfinderStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No generator for: " + type);
        }
        strategy.run(grid);
    }

    public void init(Grid grid, PathfinderType type){
        PathfinderStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No generator for: " + type);
        }
        strategy.init(grid, type);
    }

    public SearchStatus step(PathfinderType type){
        PathfinderStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No generator for: " + type);
        }
        return strategy.step();
    }

    public void reconstructPath(PathfinderType type){
        PathfinderStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No generator for: " + type);
        }
        strategy.reconstructPath();
    }
}
