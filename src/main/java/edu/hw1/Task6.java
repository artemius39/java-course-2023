package edu.hw1;

import java.util.Arrays;

public final class Task6 {
    private Task6() {
    }

    private static final int KAPREKAR_CONSTANT = 6174;
    private static final int NUMBER_LENGTH = 4;
    private static final int RADIX = 10;
    private static final int LOWER_BOUND = (int) Math.pow(RADIX, NUMBER_LENGTH - 1);
    private static final int UPPER_BOUND = (int) Math.pow(RADIX, NUMBER_LENGTH);

    public static int countK(int number) {
        if (number >= UPPER_BOUND || number < LOWER_BOUND) {
            throw new IllegalArgumentException("countK only accepts 4-digit numbers");
        }
        if (isRepdigit(number)) {
            throw new IllegalArgumentException("Number cannot be a repdigit: it must have at least 2 distinct digits");
        }

        return countKImpl(number);
    }

    private static int[] getDigits(int number) {
        int currentNumber = number;
        int[] digits = new int[NUMBER_LENGTH];
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            digits[i] = currentNumber % RADIX;
            currentNumber /= RADIX;
        }

        return digits;
    }

    private static boolean isRepdigit(int number) {
        int[] digits = getDigits(number);
        int digit = digits[0];
        for (int i = 1; i < NUMBER_LENGTH; i++) {
            if (digits[i] != digit) {
                return false;
            }
        }
        return true;
    }

    private static int countKImpl(int number) {
        if (number == KAPREKAR_CONSTANT) {
            return 0;
        }

        int[] digits = getDigits(number);
        Arrays.sort(digits);
        int nextNumber = Math.abs(digitsAscending(digits) - digitsDescending(digits));

        return countKImpl(nextNumber) + 1;
    }

    private static int digitsAscending(int[] digits) {
        int digitsAscending = 0;
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            digitsAscending = digitsAscending * RADIX + digits[i];
        }
        return digitsAscending;
    }

    private static int digitsDescending(int[] digits) {
        int digitsDescending = 0;
        for (int i = NUMBER_LENGTH - 1; i >= 0; i--) {
            digitsDescending = digitsDescending * RADIX + digits[i];
        }
        return digitsDescending;
    }
}
