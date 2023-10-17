package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Task5Test {
    private void test(int number, boolean isPalindromeDescendant) {
        assertThat(Task5.isPalindromeDescendant(number)).isEqualTo(isPalindromeDescendant);
    }

    @ParameterizedTest
    @DisplayName("Palindromes")
    @ValueSource(ints = {22, 323, 4554, 123454321})
    public void palindromes(int palindrome) {
        test(palindrome, true);
    }

    @Test
    @DisplayName("Zero")
    public void zero() {
        test(0, true);
    }

    @Test
    @DisplayName("Negative number")
    public void negativeNumber() {
        test(-1, false);
    }

    @Test
    @DisplayName("First-generation descendant is a palindrome")
    public void firstGeneration() {
        test(123312, true);
    }

    @Test
    @DisplayName("Distant descendant is a palindrome")
    public void secondGeneration() {
        test(13001120, true); // 13001120 -> 4022 -> 44
    }

    @Test
    @DisplayName("Not a palindrome ancestor")
    public void notAncestor() {
        test(23, false); // 23 -> 5
    }

    @Test
    @DisplayName("One-digit number")
    public void oneDigit() {
        test(7, true);
    }

    @Test
    @DisplayName("Odd-length descendant")
    public void oddLengthDescendant() {
        test(342333, false); // 342333 -> 756
    }

    @Test
    @DisplayName("Digit sum greater than 9")
    public void sumGreaterThanNine() {
        test(9999, true); // 9999 -> 1818 -> 99
    }
}
