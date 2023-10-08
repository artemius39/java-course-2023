package edu.hw1;

import java.util.Objects;

public final class Task8 {
    private static final int BOARD_SIZE = 8;
    private static final int KNIGHT = 1;
    private static final int EMPTY = 0;
    private static final String INVALID_BOARD_SIZE_MSG = "The board must be 8 by 8";

    private Task8() {
    }

    public static boolean knightBoardCapture(int[][] board) {
        Objects.requireNonNull(board);
        checkBoardSize(board);
        checkBoardContents(board);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    continue;
                }
                if (hasKnight(board, i + 2, j + 1)
                        || hasKnight(board, i + 2, j - 1)
                        || hasKnight(board, i - 2, j + 1)
                        || hasKnight(board, i - 2, j - 1)
                        || hasKnight(board, i + 1, j + 2)
                        || hasKnight(board, i + 1, j - 2)
                        || hasKnight(board, i - 1, j - 2)
                        || hasKnight(board, i - 1, j + 2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void checkBoardSize(int[][] board) {
        if (board.length != BOARD_SIZE) {
            throw new IllegalArgumentException(INVALID_BOARD_SIZE_MSG);
        }
        for (int[] row : board) {
            if (row.length != BOARD_SIZE) {
                throw new IllegalArgumentException(INVALID_BOARD_SIZE_MSG);
            }
        }
    }

    private static void checkBoardContents(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell != KNIGHT && cell != EMPTY) {
                    throw new IllegalArgumentException("The board must contain 0's and 1's only");
                }
            }
        }
    }

    private static boolean hasKnight(int[][] board, int i, int j) {
        return 0 <= i && i < BOARD_SIZE && 0 <= j && j < BOARD_SIZE && board[i][j] == KNIGHT;
    }
}
