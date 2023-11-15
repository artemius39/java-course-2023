package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.AT_LEAST_THREE_CHARACTERS_AND_THIRD_CHARACTER_IS_ZERO;
import static org.assertj.core.api.Assertions.assertThat;

class AtLeastThreeCharactersAndThirdCharacterIsZeroTest {
    @ParameterizedTest
    @ValueSource(strings = {"000", "1100", "000000", "11011111"})
    @DisplayName("Valid strings")
    void validStrings(String validString) {
        assertThat(validString).matches(AT_LEAST_THREE_CHARACTERS_AND_THIRD_CHARACTER_IS_ZERO);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "0", "00", "1", "11"})
    @DisplayName("Less than 3 characters")
    void lessThanThreeCharacters(String shortString) {
        assertThat(shortString).doesNotMatch(AT_LEAST_THREE_CHARACTERS_AND_THIRD_CHARACTER_IS_ZERO);
    }

    @ParameterizedTest
    @DisplayName("Third character is not zero")
    @ValueSource(strings = {"111", "00100000", "11111111"})
    void thirdCharacterIsNotZero(String invalidString) {
        assertThat(invalidString).doesNotMatch(AT_LEAST_THREE_CHARACTERS_AND_THIRD_CHARACTER_IS_ZERO);
    }

    @Test
    @DisplayName("Invalid characters")
    void invalidCharacters() {
        assertThat("ab0cdef").doesNotMatch(AT_LEAST_THREE_CHARACTERS_AND_THIRD_CHARACTER_IS_ZERO);
    }
}
