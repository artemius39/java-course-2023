package edu.project2;

import java.util.ArrayDeque;
import java.util.Queue;

public class BFSSolver extends GraphTraversingSolver {
    @Override
    protected Cell[][] traverse(Maze maze, Cell start) {
        Queue<Cell> queue = new ArrayDeque<>();
        queue.add(start);
        Cell[][] parents = new Cell[maze.getRows()][maze.getCols()];
        setParent(parents, start, start);

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            neighbors(cell, maze, parents).forEach(neighbor -> {
                queue.add(neighbor);
                setParent(parents, neighbor, cell);
            });
        }

        return parents;
    }
}
