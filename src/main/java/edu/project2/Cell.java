package edu.project2;

public record Cell(Coordinate coordinate, Type type) {
    public enum Type {
        PASSAGE, WALL
    }

    public int row() {
        return coordinate.row();
    }

    public int col() {
        return coordinate.col();
    }
}
