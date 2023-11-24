package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.SAME_FIRST_AND_LAST_CHARACTERS;
import static org.assertj.core.api.Assertions.assertThat;

class SameFirstAndLastCharacterTest {
    @Test
    @DisplayName("Both zeros")
    void bothZeros() {
        assertThat("01110000").matches(SAME_FIRST_AND_LAST_CHARACTERS);
    }

    @Test
    @DisplayName("Both ones")
    void bothOnes() {
        assertThat("11001").matches(SAME_FIRST_AND_LAST_CHARACTERS);
    }

    @Test
    @DisplayName("Starts with one, ends with zero")
    void startsWithOneEndsWithZero() {
        assertThat("1000").doesNotMatch(SAME_FIRST_AND_LAST_CHARACTERS);
    }

    @Test
    @DisplayName("Starts with zero, ends with one")
    void startsWithZeroEndWithOne() {
        assertThat("00001001111").doesNotMatch(SAME_FIRST_AND_LAST_CHARACTERS);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "0"})
    @DisplayName("One character long strings")
    void oneCharacterLongStrings(String oneCharacterLongString) {
        assertThat(oneCharacterLongString).matches(SAME_FIRST_AND_LAST_CHARACTERS);
    }

    @Test
    @DisplayName("Other characters")
    void otherCharacters() {
        assertThat("a100101a").doesNotMatch(SAME_FIRST_AND_LAST_CHARACTERS);
    }

    @Test
    @DisplayName("Empty string")
    void emptyString() {
        assertThat("").doesNotMatch(SAME_FIRST_AND_LAST_CHARACTERS);
    }
}
