package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.hw1.Task7.rotateLeft;
import static edu.hw1.Task7.rotateRight;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task7Test {
    private void testRight(int number, int shift, int expected) {
        assertThat(rotateRight(number, shift)).isEqualTo(expected);
    }

    private void testLeft(int number, int shift, int expected) {
        assertThat(rotateLeft(number, shift)).isEqualTo(expected);
    }

    private void assertError(int number, int shift) {
        assertThrows(IllegalArgumentException.class, () -> rotateLeft(number, shift));
        assertThrows(IllegalArgumentException.class, () -> rotateRight(number, shift));
    }

    @Test
    @DisplayName("Basic cases")
    public void basicTest() {
        testRight(0b1000, 1, 0b0100);
        testLeft(0b10000, 1, 0b00001);
        testLeft(0b10001, 2, 0b00110);
    }

    @Test
    @DisplayName("Negative shift")
    public void negativeShift() {
        assertError(42, -1);
    }

    @Test
    @DisplayName("Zero")
    public void zero() {
        assertError(0, 42);
    }

    @Test
    @DisplayName("Negative number")
    public void negativeNumber() {
        assertError(-1, 42);
    }

    @Test
    @DisplayName("Zero-length shift")
    public void zeroLength() {
        testLeft(42, 0, 42);
        testRight(18, 0, 18);
    }

    @Test
    @DisplayName("Limits")
    public void limits() {
        testRight(Integer.MAX_VALUE, 10, Integer.MAX_VALUE);
        testLeft(Integer.MAX_VALUE, 15, Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("Long shift")
    public void longShift() {
        assertThat(rotateLeft(0b1001_0010_0001, 145)).isEqualTo(rotateLeft(0b1001_0010_0001, 145 % 12));
        assertThat(rotateRight(0b10_0001, 121)).isEqualTo(rotateRight(0b10_0001, 121 % 6));
    }

    @Test
    @DisplayName("Very long shift")
    public void veryLongShift() {
        assertThat(rotateLeft(0b1101, Integer.MAX_VALUE)).isEqualTo(rotateLeft(0b1101, Integer.MAX_VALUE % 4));
    }
}
