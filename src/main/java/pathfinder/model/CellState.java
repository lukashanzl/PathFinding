package pathfinder.model;

import java.util.HashMap;
import java.util.Map;

public enum CellState {
    Empty(0),
    Path(1),
    Wall(2);

    private final int value;
    private static final Map<Integer, CellState> map = new HashMap<>();

    CellState(int value) {
        this.value = value;
    }

    static {
        for (CellState state : CellState.values()) {
            map.put(state.value, state);
        }
    }

    public static CellState valueOf(int value) {
        return (CellState) map.get(value);
    }

    public int getValue() {
        return value;
    }
}
