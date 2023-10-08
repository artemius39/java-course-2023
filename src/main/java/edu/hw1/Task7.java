package edu.hw1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Task7 {
    private static final int INT_SIZE = 32;

    private static final String NEGATIVE_SHIFT_ERROR_MSG = "Negative shift";

    private Task7() {
    }

    private static int rotateImpl(int n, int shift) {
        if (n <= 0) {
            throw new IllegalArgumentException("Only positive numbers can be shifted");
        }

        List<Integer> bits = getBits(n);
        Collections.rotate(bits, shift);

        int result = 0;
        for (int i = bits.size() - 1; i >= 0; i--) {
            result = result * 2 + bits.get(i);
        }
        return result;
    }

    private static List<Integer> getBits(int n) {
        List<Integer> bits = new ArrayList<>();
        for (int i = 0; i < INT_SIZE; i++) {
            if (testBit(n, i)) {
                while (bits.size() < i) {
                    bits.add(0);
                }
                bits.add(1);
            }
        }
        return bits;
    }

    private static boolean testBit(int n, int bit) {
        return (n & (1 << bit)) != 0;
    }

    public static int rotateRight(int n, int shift) {
        if (shift < 0) {
            throw new IllegalArgumentException(NEGATIVE_SHIFT_ERROR_MSG);
        }
        return rotateImpl(n, -shift);
    }

    public static int rotateLeft(int n, int shift) {
        if (shift < 0) {
            throw new IllegalArgumentException(NEGATIVE_SHIFT_ERROR_MSG);
        }
        return rotateImpl(n, shift);
    }
}
