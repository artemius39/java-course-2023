package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {
    private void test(String time, long expectedSeconds) {
        assertThat(Task1.minutesToSeconds(time)).isEqualTo(expectedSeconds);
    }

    private void test(String time, long expectedMinutes, long expectedSeconds) {
        test(time, expectedMinutes * 60 + expectedSeconds);
    }

    private void test(long expectedMinutes, long expectedSeconds) {
        test(expectedMinutes, expectedSeconds, 0);
    }

    private void test(long expectedMinutes, long expectedSeconds, int trailingZeroes) {
        test(timeString(expectedMinutes, expectedSeconds, trailingZeroes), expectedMinutes, expectedSeconds);
    }

    private String timeString(long minutes, long seconds, int trailingZeroes) {
        return "0".repeat(trailingZeroes) + String.format("%02d:%02d", minutes, seconds);
    }

    private void assertError(String time) {
        assertThat(Task1.minutesToSeconds(time)).isEqualTo(-1);
    }

    @Test
    @DisplayName("Basic case")
    public void basicTest() {
        test(12, 44);
    }

    @ParameterizedTest
    @DisplayName("Long time")
    @CsvSource({
            "10000, 45",
            "123456789101112, 39",
    })
    public void longTime(long longMinutes, long seconds) {
        test(longMinutes, seconds);
    }

    @Test
    @DisplayName("59 seconds")
    public void maxSeconds() {
        test(13, 59);
    }

    @Test
    @DisplayName("Zero seconds")
    public void zeroSeconds() {
        test(42, 0);
    }

    @Test
    @DisplayName("Zero time")
    public void zero() {
        test("00:00", 0);
    }

    @ParameterizedTest
    @DisplayName("Trailing zeroes")
    @CsvSource({"1", "2", "3", "10", "10000"})
    public void trailingZeroes(int zeroes) {
        test(12, 34, zeroes);
    }

    @Test
    @DisplayName("Max time")
    public void maxTime() {
        test("153722867280912930:07", Long.MAX_VALUE);
    }

    @Test
    @DisplayName("Null string")
    public void nullTime() {
        assertThrows(NullPointerException.class, () -> Task1.minutesToSeconds(null));
    }

    @Test
    @DisplayName("Empty string")
    public void emptyTime() {
        assertError("");
    }

    @ParameterizedTest
    @DisplayName("Too many seconds")
    @ValueSource(strings = {"60", "100"})
    public void tooManySeconds(String seconds) {
        assertError("05:" + seconds);
    }

    @ParameterizedTest
    @DisplayName("Less than 2 digits for minutes or seconds")
    @ValueSource(strings = {"", "0"})
    public void shortMinutes(String shortNumber) {
        assertError(shortNumber + ":00");
        assertError("00:" + shortNumber);
    }

    @ParameterizedTest
    @DisplayName("Negative numbers")
    @ValueSource(strings = {"-1:00", "12:-10", "12:-0"})
    public void negativeNumbers(String time) {
        assertError(time);
    }

    @Test
    @DisplayName("Trailing spaces")
    public void spaces() {
        assertError("   12:00 ");
    }

    @Test
    @DisplayName("Overflow")
    public void overflow() {
        assertError("1" + "0".repeat(19) + ":00");
        assertError("1" + "0".repeat(18) + ":00");
    }
}
