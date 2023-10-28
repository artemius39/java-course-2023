package edu.project2;

import java.util.List;

public class SimpleRenderer implements MazeRenderer {
    // one is for left wall, one is for right wall, and one is for line feed
    private static final int ADDITIONAL_COLUMNS = 3;
    private static final int ADDITIONAL_ROWS = 1; // top wall
    private final char wallChar;
    private final char passageChar;
    private final char pathChar;
    private final char startEndChar;

    private void setCharAt(StringBuilder sb, Coordinate coordinate, int cols, char ch) {
        int index = (coordinate.row() + ADDITIONAL_ROWS) * (cols + ADDITIONAL_COLUMNS) + coordinate.col() + 1;
        sb.setCharAt(index, ch);
    }

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

    @Override
    public String render(Maze maze) {
        StringBuilder sb = renderWalls(maze);

        Coordinate start = maze.getStart();
        Coordinate end = maze.getEnd();
        if (start != null /*&& end != null*/) {
            setCharAt(sb, start, maze.getCols(), startEndChar);
            setCharAt(sb, end, maze.getCols(), startEndChar);
        }
        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = renderWalls(maze);
        if (path == null) {
            return sb.toString();
        }
        for (Coordinate coordinate : path) {
            setCharAt(sb, coordinate, maze.getCols(), pathChar);
        }
        return sb.toString();
    }

    private StringBuilder renderWalls(Maze maze) {
        StringBuilder sb = new StringBuilder(maze.getRows() * (maze.getCols() + 1));

        sb.append(String.valueOf(wallChar).repeat(maze.getCols() + 2)).append('\n');
        for (int row = 0; row < maze.getRows(); row++) {
            sb.append(wallChar);
            for (int col = 0; col < maze.getCols(); col++) {
                sb.append(maze.cellAt(row, col).type() == Cell.Type.WALL ? wallChar : passageChar);
            }
            sb.append(wallChar).append('\n');
        }
        sb.append(String.valueOf(wallChar).repeat(maze.getCols() + 2));

        return sb;
    }
}
