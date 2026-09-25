# PathFinding

A simple application with a GUI to implement, test, and visualize algorithms.

Built with Java Swing, it generates mazes and then searches them for a path,
drawing every step on a grid so you can watch the algorithms work.

## Features

- **Grid visualization** — a resizable grid of cells rendered with Swing, each
  cell colored by its state (wall, empty, start, end, path).
- **Maze generation**
  - Depth-First (recursive backtracker) — carves 1-cell-thick walls into a
    perfect maze.
- **Pathfinding**
  - A\* — searches the generated maze from start to end using a Manhattan-distance
    heuristic.
- **Interactive controls** — a side panel with one button per algorithm; click to
  generate a maze or run a pathfinder and see the result immediately.

## Tech stack

- **Java 26**
- **Swing** (AWT/Swing GUI, no external UI framework)
- **Maven** for build
- **Lombok** for boilerplate (getters/setters)

## Project structure

```
src/main/java/
├── Application.java                     # entry point: builds the window, wires controls
└── pathfinder/
    ├── Grid.java                        # model: 2D array of cells
    ├── GridPanel.java                   # view: draws the grid
    ├── ControlPanel.java               # view: algorithm buttons
    ├── model/                           # Cell, AStarCell, CellState, enums, constants
    └── service/
        ├── maze/                        # MazeGenerator + strategies (DepthFirst)
        ├── pathfinder/                  # Pathfinder + strategies (AStar)
        └── utils/                       # shared helpers
```

The design keeps a clean separation of concerns: **algorithms operate only on the
`Grid` model**, the **view** just renders whatever state it finds, and
`Application` wires the two together. Each algorithm family is pluggable behind a
strategy interface, so new maze or pathfinding methods can be added without
touching the UI.

## Running

**From an IDE:** run the `Application` class.

**From the command line:**

```bash
mvn compile
java -cp target/classes Application
```

## Usage

1. Launch the app — an empty grid appears with a control panel on the left.
2. Under **Maze Generation**, click **Depth-First** to carve a maze.
3. Under **Pathfinders**, click **A\* Pathfinder** to find a route from the start
   (top-left) to the end (bottom-right).

## Roadmap

- Additional maze generators (e.g. Prim's, Kruskal's)
- Additional pathfinders (e.g. BFS, Dijkstra)
- Step-by-step animation of the search
