package edu.hw7.task2;

import java.util.stream.LongStream;

public final class ParallelFactorial {
    public static long factorial(long number) {
        if (number < 0) {
            throw new IllegalArgumentException("Factorial of negative number: " + number);
        }

        return LongStream.rangeClosed(1, number)
                .parallel()
                .reduce(1, (a, b) -> a * b);
    }

    private ParallelFactorial() {
    }
}
