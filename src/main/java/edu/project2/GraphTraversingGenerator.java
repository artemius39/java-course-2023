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
        if (rows % 2 == 0) {
            for (int col = 0; col < cols; col += 2) {
                if (RANDOM.nextBoolean()) {
                    cells[rows - 1][col] = new Cell(new Coordinate(rows - 1, col), Cell.Type.PASSAGE);
                }
            }
        }
        if (cols % 2 == 0) {
            for (int row = 0; row < rows; row += 2) {
                if (RANDOM.nextBoolean()) {
                    cells[row][cols - 1] = new Cell(new Coordinate(row, cols - 1), Cell.Type.PASSAGE);
                }
            }
        }

        return new Maze(
                rows, cols, cells,
                new Coordinate(0, randomEvenNumber(cols)),
                new Coordinate(lastEvenRow(rows), randomEvenNumber(cols))
        );
    }

    private int lastEvenRow(int rows) {
        return rows - (2 - rows % 2); // same as 'rows % 2 == 0 ? rows - 2 : rows - 1'
    }

    private int randomEvenNumber(int bound) {
        return RANDOM.nextInt((bound + 1) / 2) * 2;
    }
}
