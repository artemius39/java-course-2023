package edu.hw3.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static edu.hw3.task1.Task1.atbashCipher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task1Test {
    private void test(String stringToBeCiphered, String expected) {
        String atbashed = atbashCipher(stringToBeCiphered);

        assertThat(atbashed).isEqualTo(expected);
    }

    @Test
    @DisplayName("Null string")
    void nullString() {
        assertThrows(NullPointerException.class, () -> atbashCipher(null));
    }

    @Test
    @DisplayName("Empty string")
    void emptyString() {
        test("", "");
    }

    @ParameterizedTest
    @CsvSource({"a, z", "bibaboba, yryzylyz"})
    @DisplayName("Lowercase characters")
    void lowercaseCharacters(String stringToBeCiphered, String expected) {
        test(stringToBeCiphered, expected);
    }

    @ParameterizedTest
    @CsvSource({"A, Z", "BIBABOBA, YRYZYLYZ"})
    @DisplayName("Uppercase characters")
    void uppercaseCharacters(String stringToBeCiphered, String expected) {
        test(stringToBeCiphered, expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"!", "$", "    ", "\n\n\n\n\n", "///////"})
    @DisplayName("Non-latin characters")
    void nonLatinCharacters(String stringToBeCiphered) {
        test(stringToBeCiphered, stringToBeCiphered);
    }

    @Test
    @DisplayName("Mixed characters")
    void mixedCharacters() {
        test("Hello world!", "Svool dliow!");
    }

    @Test
    @DisplayName("Long string")
    void longString() {
        test(
                "Any fool can write code that a computer can understand. Good programmers write code " +
                        "that humans can understand. ― Martin Fowler",
                "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg " +
                        "sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi"
        );
    }
}
