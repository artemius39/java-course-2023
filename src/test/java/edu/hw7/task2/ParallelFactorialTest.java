package edu.hw7.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw7.task2.ParallelFactorial.factorial;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParallelFactorialTest {
    @Test
    @DisplayName("Factorial of zero")
    void factorialOfZeroIsOne() {
        long factorial = factorial(0);

        assertThat(factorial).isOne();
    }

    @Test
    @DisplayName("Positive number")
    void positiveNumber() {
        long factorial = factorial(10);

        assertThat(factorial).isEqualTo(2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10);
    }

    @Test
    @DisplayName("Negative number")
    void negativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> factorial(-1));
    }
}
