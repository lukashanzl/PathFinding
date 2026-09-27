package pathfinder.model;

import lombok.Getter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum CellState {
    Empty(0, Color.white),
    Path(1, Constants.COLOR_PATH),
    Wall(2, Constants.COLOR_WALL),
    Start(3, Color.blue),
    End(4, Color.magenta),
    Visited(5, Color.yellow);

    private final int value;
    private final Color color;
    private static final Map<Integer, CellState> map = new HashMap<>();

    CellState(int value, Color color) {
        this.value = value;
        this.color = color;
    }

    static {
        for (CellState state : CellState.values()) {
            map.put(state.value, state);
        }
    }

    public static CellState valueOf(int value) {
        return map.get(value);
    }
}
