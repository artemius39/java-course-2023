package edu.hw1;

import java.util.Objects;

public final class Task3 {
    private Task3() {
    }

    private static int min(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int number : array) {
            min = Math.min(min, number);
        }
        return min;
    }

    private static int max(int[] array) {
        int min = Integer.MIN_VALUE;
        for (int number : array) {
            min = Math.max(min, number);
        }
        return min;
    }

    public static boolean isNestable(int[] smaller, int[] bigger) {
        Objects.requireNonNull(smaller);
        Objects.requireNonNull(bigger);

        if (smaller.length == 0 || bigger.length == 0) {
            return false;
        }

        return min(bigger) < min(smaller) && max(smaller) < max(bigger);
    }
}
