package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    private void test(long number, int count) {
        assertThat(Task2.countDigits(number)).isEqualTo(count);
    }

    @ParameterizedTest
    @DisplayName("Basic cases")
    @CsvSource({
            "1, 1",
            "13, 2",
            "123, 3",
            "1024, 4",
            "12345, 5"
    })
    public void basicTest(long number, int count) {
        test(number, count);
    }

    @ParameterizedTest
    @DisplayName("Negative numbers")
    @CsvSource({
            "-1, 1",
            "-11, 2",
            "-1004, 4",
            "-99999, 5"
    })
    public void negativeNumbers(long number, int count) {
        test(number, count);
    }

    @ParameterizedTest
    @DisplayName("Powers of 10")
    @CsvSource({
            "10, 2",
            "100, 3",
            "1000, 4",
            "1000000000000000000, 19"
    })
    public void powersOfTen(long number, int count) {
        test(number, count);
        test(-number, count);
    }

    @Test
    @DisplayName("Zero")
    public void zero() {
        test(0, 1);
    }

    @Test
    @DisplayName("Max value")
    public void maxValue() {
        test(Long.MAX_VALUE, 19);
    }

    @Test
    @DisplayName("Min value")
    public void minValue() {
        test(Long.MIN_VALUE, 19);
    }
}
