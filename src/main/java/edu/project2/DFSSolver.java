package edu.project2;

public class DFSSolver extends GraphTraversingSolver {
    @Override
    protected Cell[][] traverse(Maze maze, Cell start) {
        Cell[][] parents = new Cell[maze.getRows()][maze.getCols()];
        dfs(start, start, parents, maze);
        return parents;
    }

    private void dfs(Cell cell, Cell parent, Cell[][] parents, Maze maze) {
        setParent(parents, cell, parent);
        neighbors(cell, maze, parents).forEach(neighbor -> dfs(neighbor, cell, parents, maze));
    }
}
