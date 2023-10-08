package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.hw1.Task8.knightBoardCapture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task8Test {

    private void assertError(int[][] board) {
        assertThrows(IllegalArgumentException.class, () -> knightBoardCapture(board));
    }

    private void test(int[][] board, boolean expected) {
        assertThat(knightBoardCapture(board)).isEqualTo(expected);
    }

    private int[][] makeBoard(int... knights) {
        if (knights.length % 2 == 1) {
            throw new IllegalArgumentException("Missing column number for last knight");
        }
        int[][] board = new int[8][8];
        for (int i = 0; i < knights.length; i += 2) {
            board[knights[i]][knights[i + 1]] = 1;
        }
        return board;
    }

    @Test
    @DisplayName("Null board")
    public void nullBoard() {
        assertThrows(NullPointerException.class, () -> knightBoardCapture(null));
    }

    @Test
    @DisplayName("Wrong size board")
    public void wrongSizeBoard() {
        assertError(new int[3][8]);
        assertError(new int[8][4]);

        int[][] board = new int[8][8];
        board[3] = new int[4];
        assertError(board);
    }

    @Test
    @DisplayName("Wrong values in board")
    public void wrongValues() {
        int[][] board = new int[8][8];
        board[4][2] = 42;
        assertError(board);
    }

    @Test
    @DisplayName("Empty board")
    public void empty() {
        test(new int[8][8], true);
    }

    @Test
    @DisplayName("Two knights don't capture")
    public void twoKnights() {
        int[][] board = new int[8][8];
        board[0][0] = 1;
        board[7][7] = 1;

        test(board, true);
    }

    @Test
    @DisplayName("Two knights capture")
    public void twoKnightsOneBoard() {
        int[][] board = new int[8][8];
        board[0][0] = 1;
        board[1][2] = 1;

        test(board, false);
    }

    @Test
    @DisplayName("Multiple knights")
    public void multipleKnights() {
        int[][] board = makeBoard(
                0, 3,
                2, 1,
                2, 5,
                3, 4,
                3, 6,
                4, 1,
                4, 5,
                6, 1,
                7, 4
        );

        test(board, true);
    }
}
