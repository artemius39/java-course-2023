package edu.project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

interface MazeGeneratorTest<T extends MazeGenerator> {
    T createGenerator();

    private void test(int rows, int cols) {
        MazeGenerator generator = createGenerator();
        Maze maze = generator.generate(rows, cols);

        assertThat(maze).isNotNull();
        assertThat(maze.getRows()).isEqualTo(rows);
        assertThat(maze.getCols()).isEqualTo(cols);
        assertPassage(maze, maze.getStart());
        assertPassage(maze, maze.getEnd());

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                testCell(maze.cellAt(row, col), row, col);
            }
        }
    }

    private void assertPassage(Maze maze, Coordinate coordinate) {
        assertThat(coordinate).isNotNull();
        assertThat(maze.cellAt(coordinate).type()).isEqualTo(Cell.Type.PASSAGE);
    }

    private void testCell(Cell cell, int row, int col) {
        assertThat(cell).isNotNull();
        assertThat(cell.row()).isEqualTo(row);
        assertThat(cell.col()).isEqualTo(col);
        assertThat(cell.type()).isNotNull();
        assertThat(cell.coordinate()).isNotNull();
        assertThat(cell.coordinate().row()).isEqualTo(row);
        assertThat(cell.coordinate().col()).isEqualTo(col);
    }

    private void assertError(int rows, int cols) {
        MazeGenerator generator = createGenerator();

        assertThrows(IllegalArgumentException.class, () -> generator.generate(rows, cols));
    }

    @Test
    @DisplayName("Basic case")
    default void basicCase() {
        test(42, 29);
    }

    @Test
    @DisplayName("Zero rows")
    default void zeroRows() {
        assertError(0, 42);
    }

    @Test
    @DisplayName("Negative rows")
    default void negativeRows() {
        assertError(-42, 42);
    }

    @Test
    @DisplayName("Zero cols")
    default void zeroCols() {
        assertError(42, 0);
    }

    @Test
    @DisplayName("Negative cols")
    default void negativeCols() {
        assertError(42, -42);
    }

    @Test
    @DisplayName("1 row")
    default void oneRow() {
        test(1, 42);
    }

    @Test
    @DisplayName("1 col")
    default void oneCol() {
        test(42, 1);
    }

    @Test
    @DisplayName("1x1 maze")
    default void oneByOneMaze() {
        test(1, 1);
    }
}
