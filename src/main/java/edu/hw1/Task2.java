package edu.hw1;

public final class Task2 {
    private Task2() {
    }

    private static final int RADIX = 10;

    public static int countDigits(final long number) {
        if (number == 0) {
            return 1;
        }

        // Make all numbers negative instead of positive to avoid overflow
        long currentNumber = -Math.abs(number);
        int answer = 0;
        while (currentNumber < 0) {
            currentNumber /= RADIX;
            answer++;
        }
        return answer;
    }
}
