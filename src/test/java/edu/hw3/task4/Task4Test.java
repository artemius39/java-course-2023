package edu.hw3.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static edu.hw3.task4.Task4.convertToRoman;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task4Test {
    @ParameterizedTest
    @ValueSource(ints = {-100, 0, 4000, 5000, Integer.MIN_VALUE, Integer.MAX_VALUE})
    @DisplayName("Illegal numbers")
    void illegalNumbers(int illegalNumber) {
        assertThrows(IllegalArgumentException.class, () -> convertToRoman(illegalNumber));
    }

    @ParameterizedTest
    @CsvSource({
            "1, I",
            "5, V",
            "10, X",
            "50, L",
            "100, C",
            "500, D",
            "1000, M"
    })
    @DisplayName("One character numbers")
    void oneCharacterNumbers(int number, String expected) {
        assertThat(convertToRoman(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "3, III",
            "4, IV",
            "9, IX",
            "7, VII"
    })
    @DisplayName("Multiple characters for a digit")
    void multipleCharacters(int number, String expected) {
        assertThat(convertToRoman(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "11, XI",
            "52, LII",
            "123, CXXIII",
            "3237, MMMCCXXXVII"
    })
    @DisplayName("Multiple digits")
    void multipleDigits(int number, String expected) {
        assertThat(convertToRoman(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "105, CV",
            "3109, MMMCIX",
            "1019, MXIX",
            "2007, MMVII"
    })
    @DisplayName("Some digits are zero")
    void someDigitsAreZero(int number, String expected) {
        assertThat(convertToRoman(number)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Max number")
    void maxNumber() {
        assertThat(convertToRoman(3999)).isEqualTo("MMMCMXCIX");
    }
}
