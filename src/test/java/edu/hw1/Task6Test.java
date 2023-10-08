package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task6Test {
    private void test(int number, int expectedIterations) {
        assertThat(Task6.countK(number)).isEqualTo(expectedIterations);
    }

    private void assertError(int number) {
        assertThrows(IllegalArgumentException.class, () -> Task6.countK(number));
    }

    @Test
    @DisplayName("Kaprekar's constant")
    public void kaprekarsConstant() {
        test(6174, 0);
    }

    @Test
    @DisplayName("One iteration")
    public void oneIteration() {
        test(2538, 1);
    }

    @Test
    @DisplayName("Multiple iterations")
    public void multipleIterations() {
        test(1211, 5);
    }

    @Test
    @DisplayName("Lower bound")
    public void lowerBound() {
        test(1000, 5);
    }

    @Test
    @DisplayName("Maximum iterations")
    public void maximumIterations() {
        test(5001, 7);
    }

    @Test
    @DisplayName("Short number")
    public void shortNumber() {
        assertError(42);
    }

    @Test
    @DisplayName("Long number")
    public void longNumber() {
        assertError(1234567);
    }

    @Test
    @DisplayName("Repdigit")
    public void repdigit() {
        assertError(1111);
    }
}
