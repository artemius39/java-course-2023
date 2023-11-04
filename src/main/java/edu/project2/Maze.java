package edu.project2;

import java.util.Objects;

public class Maze {
    private final int rows;
    private final int cols;
    private final Cell[][] cells;
    private final Coordinate start;
    private final Coordinate end;

    public Maze(int rows, int cols, Cell[][] cells, Coordinate start, Coordinate end) {
        Objects.requireNonNull(start, "start cannot be null");
        Objects.requireNonNull(end, "end cannot be null");

        this.rows = rows;
        this.cols = cols;
        this.cells = cells;
        this.start = start;
        this.end = end;
    }

    public Cell cellAt(int row, int col) {
        return cells[row][col];
    }

    public Cell cellAt(Coordinate position) {
        return cellAt(position.row(), position.col());
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
