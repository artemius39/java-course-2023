package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.EVERY_ODD_CHARACTER_IS_1;
import static org.assertj.core.api.Assertions.assertThat;

class EveryOddCharacterIs1Test {
    @ParameterizedTest
    @DisplayName("Every odd character is 1")
    @ValueSource(strings = {"1", "101", "11111", "1010111"})
    void everyOddCharacterIs1(String string) {
        assertThat(string).matches(EVERY_ODD_CHARACTER_IS_1);
    }

    @ParameterizedTest
    @DisplayName("Some odd characters are 0")
    @ValueSource(strings = {"100", "0", "00000", "1111100"})
    void someOddCharactersAreZero(String string) {
        assertThat(string).doesNotMatch(EVERY_ODD_CHARACTER_IS_1);
    }

    @Test
    @DisplayName("Empty string")
    void emptyString() {
        assertThat("").matches(EVERY_ODD_CHARACTER_IS_1);
    }
}
