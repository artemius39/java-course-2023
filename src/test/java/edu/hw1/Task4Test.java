package edu.hw1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {
    private void test(String broken, String fixed) {
        Assertions.assertThat(Task4.fixString(broken)).isEqualTo(fixed);
    }

    @Test
    @DisplayName("Basic case")
    public void basicTest() {
        test("214365", "123456");
    }

    @Test
    @DisplayName("Odd length string")
    public void oddLength() {
        test("21435", "12345");
    }

    @Test
    @DisplayName("Empty string")
    public void emptyString() {
        test("", "");
    }

    @Test
    @DisplayName("String of length 1")
    public void length1() {
        test("a", "a");
    }

    @Test
    @DisplayName("Null string")
    public void nullString() {
        assertThrows(NullPointerException.class, () -> Task4.fixString(null));
    }
}
