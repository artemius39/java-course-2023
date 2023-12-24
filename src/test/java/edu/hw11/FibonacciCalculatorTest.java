package edu.hw11;

import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FibonacciCalculatorTest {
    @Test
    @DisplayName("Fibonacci number calculation")
    void generatedMethodProducesCorrectResults() {
        long[] numbers = IntStream.rangeClosed(0, 10)
                .mapToLong(FibonacciCalculator::fib)
                .toArray();

        assertThat(numbers).containsExactly(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
    }
}
