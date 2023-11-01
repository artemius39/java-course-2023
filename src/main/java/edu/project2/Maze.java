package edu.project2;

public class Maze {
    private final int rows;
    private final int cols;
    private final Cell[][] cells;
    private final Coordinate start;
    private final Coordinate end;

    public Maze(int rows, int cols, Cell[][] cells, Coordinate start, Coordinate end) {
        if ((start == null) != (end == null)) {
            throw new IllegalArgumentException("Maze must either have both start and end or not have either");
        }

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
