package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class GraphTraversingSolver implements MazeSolver {
    protected abstract Cell[][] traverse(Maze maze, Cell start);

    protected boolean notVisited(Cell[][] parents, Cell neighbor) {
        return getParent(parents, neighbor) == null;
    }

    protected void setParent(Cell[][] parents, Cell child, Cell parent) {
        parents[child.row()][child.col()] = parent;
    }

    protected Cell getParent(Cell[][] parents, Cell cell) {
        return parents[cell.row()][cell.col()];
    }

    protected Stream<Cell> neighbors(Cell cell, Maze maze, Cell[][] parents) {
        return cell.coordinate().neighbors(maze.getRows(), maze.getCols())
                .map(maze::cellAt)
                .filter(neighbor -> neighbor.type() == Cell.Type.PASSAGE)
                .filter(neighbor -> notVisited(parents, neighbor));
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        Objects.requireNonNull(start, "start cannot be null");
        Objects.requireNonNull(end, "end cannot be null");

        Cell startCell = maze.cellAt(start);
        Cell[][] parents = traverse(maze, startCell);

        Cell endCell = maze.cellAt(end);
        if (notVisited(parents, endCell)) {
            // there is no solution
            return null;
        }

        List<Coordinate> path = new ArrayList<>();
        Cell cell = endCell;
        while (!cell.equals(startCell)) {
            path.add(cell.coordinate());
            cell = getParent(parents, cell);
        }
        path.add(start);
        Collections.reverse(path);

        return path;
    }
}
