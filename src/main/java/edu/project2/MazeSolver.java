package edu.project2;

import java.util.List;

public interface MazeSolver {
    default List<Coordinate> solve(Maze maze) {
        return solve(maze, maze.getStart(), maze.getEnd());
    }

    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
