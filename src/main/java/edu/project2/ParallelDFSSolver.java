package edu.project2;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ParallelDFSSolver extends GraphTraversingSolver {
    @Override
    protected Cell[][] traverse(Maze maze, Cell start) {
        Cell[][] parents = new Cell[maze.getRows()][maze.getCols()];
        new DFSTask(start, start, parents, maze).fork().join();
        return parents;
    }

    private class DFSTask extends RecursiveTask<Void> {
        private final Cell cell;
        private final Cell parent;
        private final Cell[][] parents;
        private final Maze maze;

        DFSTask(Cell cell, Cell parent, Cell[][] parents, Maze maze) {
            this.cell = cell;
            this.parent = parent;
            this.parents = parents;
            this.maze = maze;
        }

        @Override
        protected Void compute() {
            synchronized (cell) {
                if (!notVisited(parents, cell)) {
                    // another thread visited this cell
                    return null;
                }
                setParent(parents, cell, parent);
            }

            List<DFSTask> tasks = neighbors(cell, maze, parents)
                    .map(neighbor -> new DFSTask(neighbor, cell, parents, maze))
                    .toList();

            for (DFSTask task : tasks) {
                task.fork();
            }
            for (DFSTask task : tasks.reversed()) {
                task.join();
            }

            return null;
        }
    }
}
