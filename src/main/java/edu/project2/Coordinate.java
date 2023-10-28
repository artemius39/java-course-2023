package edu.project2;

import java.util.stream.Stream;

public record Coordinate(int row, int col) {
    public Stream<Coordinate> neighbors(int rows, int cols, int step) {
        return Stream.of(
                        new Coordinate(row - step, col),
                        new Coordinate(row, col + step),
                        new Coordinate(row + step, col),
                        new Coordinate(row, col - step)
                )
                .filter(neighbor -> 0 <= neighbor.row())
                .filter(neighbor -> neighbor.row() < rows)
                .filter(neighbor -> 0 <= neighbor.col())
                .filter(neighbor -> neighbor.col() < cols);
    }
}
