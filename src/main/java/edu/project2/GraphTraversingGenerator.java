package edu.project2;

import java.util.Random;

public abstract class GraphTraversingGenerator implements MazeGenerator {
    private static final Random RANDOM = new Random();

    protected abstract Cell[][] traverse(int rows, int cols);

    @Override
    public Maze generate(int rows, int cols) {
        if (rows <= 0) {
            throw new IllegalArgumentException("row count must be positive: " + rows);
        }
        if (cols <= 0) {
            throw new IllegalArgumentException("column count must be positive: " + cols);
        }

        Cell[][] cells = traverse(rows, cols);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (cells[row][col] == null) {
                    cells[row][col] = new Cell(new Coordinate(row, col), Cell.Type.WALL);
                }
            }
        }

        return new Maze(
                rows, cols, cells,
                new Coordinate(0, randomEvenNumber(cols)),
                new Coordinate(rows - 1, randomEvenNumber(cols))
        );
    }

    private int randomEvenNumber(int bound) {
        return RANDOM.nextInt((bound + 1) / 2) * 2;
    }
}
