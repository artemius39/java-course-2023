package edu.project2;

public final class TestUtils {
    private TestUtils() {
    }

    public static Maze parseMaze(String mazeString) {
        String[] maze = mazeString.split("\n");
        int rows = maze.length;
        int cols = maze[0].length();
        Cell[][] cells = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Cell(
                        new Coordinate(row, col),
                        maze[row].charAt(col) == '#' ? Cell.Type.WALL : Cell.Type.PASSAGE
                );
            }
        }

        return new Maze(rows, cols, cells, findStart(mazeString), findEnd(mazeString));
    }

    public static Coordinate findStart(String mazeString) {
        return find('s', mazeString);
    }

    public static Coordinate findEnd(String mazeString) {
        return find('e', mazeString);
    }

    public static Coordinate find(char ch, String mazeString) {
        String[] maze = mazeString.split("\n");
        int rows = maze.length;
        int cols = maze[0].length();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (maze[row].charAt(col) == ch) {
                    return new Coordinate(row, col);
                }
            }
        }
        return null;
    }
}
