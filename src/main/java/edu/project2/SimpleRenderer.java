package edu.project2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleRenderer implements MazeRenderer {
    private static final int UPPER_WALL = 1;
    private static final int LOWER_WALL = 1;
    private static final int ADDITIONAL_ROWS = UPPER_WALL + LOWER_WALL;
    private static final int LEFT_WALL = 1;
    private static final int RIGHT_WALL = 1;
    private static final int ADDITIONAL_COLUMNS = LEFT_WALL + RIGHT_WALL;
    private final char wallChar;
    private final char passageChar;
    private final char pathChar;
    private final char startEndChar;

    public SimpleRenderer() {
        // with this wall character, maze looks best at line height of 0.9
        this('█', ' ', '•', 'x');
    }

    public SimpleRenderer(char wallChar, char passageChar, char pathChar, char startEndChar) {
        this.wallChar = wallChar;
        this.passageChar = passageChar;
        this.pathChar = pathChar;
        this.startEndChar = startEndChar;
    }

    private char[][] renderWalls(Maze maze) {
        int renderRows = maze.getRows() + ADDITIONAL_ROWS;
        int renderCols = maze.getCols() + ADDITIONAL_COLUMNS;
        char[][] chars = new char[renderRows][renderCols];

        Arrays.fill(chars[0], wallChar);
        Arrays.fill(chars[renderRows - 1], wallChar);
        for (int row = 0; row < maze.getRows(); row++) {
            chars[row + UPPER_WALL][0] = wallChar;
            for (int col = 0; col < maze.getCols(); col++) {
                setCharAt(chars, row, col, maze.cellAt(row, col).type() == Cell.Type.WALL ? wallChar : passageChar);
            }
            chars[row + UPPER_WALL][renderCols - 1] = wallChar;
        }

        return chars;
    }

    private String flatten(char[][] chars) {
        return Stream.of(chars)
                .map(String::new)
                .collect(Collectors.joining("\n"));
    }

    private void setCharAt(char[][] chars, Coordinate coordinate, char ch) {
        setCharAt(chars, coordinate.row(), coordinate.col(), ch);
    }

    private void setCharAt(char[][] chars, int row, int col, char ch) {
        chars[row + UPPER_WALL][col + LEFT_WALL] = ch;
    }

    private void renderStartOrEnd(Coordinate startOrEnd, char[][] chars) {
        setCharAt(chars, startOrEnd, startEndChar);
    }

    @Override
    public String render(Maze maze) {
        char[][] chars = renderWalls(maze);

        renderStartOrEnd(maze.getStart(), chars);
        renderStartOrEnd(maze.getEnd(), chars);

        return flatten(chars);
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        char[][] chars = renderWalls(maze);
        if (path != null) {
            for (Coordinate coordinate : path) {
                setCharAt(chars, coordinate, pathChar);
            }
        }
        return flatten(chars);
    }
}
