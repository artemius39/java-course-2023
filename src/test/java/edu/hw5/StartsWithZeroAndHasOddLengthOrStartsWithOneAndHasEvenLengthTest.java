package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.STARTS_WITH_ZERO_AND_HAS_ODD_LENGTH_OR_STARTS_WITH_ONE_AND_HAS_EVEN_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;

class StartsWithZeroAndHasOddLengthOrStartsWithOneAndHasEvenLengthTest {
    private void assertMatch(String string) {
        assertThat(string).matches(STARTS_WITH_ZERO_AND_HAS_ODD_LENGTH_OR_STARTS_WITH_ONE_AND_HAS_EVEN_LENGTH);
    }

    private void assertNoMatch(String string) {
        assertThat(string).doesNotMatch(STARTS_WITH_ZERO_AND_HAS_ODD_LENGTH_OR_STARTS_WITH_ONE_AND_HAS_EVEN_LENGTH);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "011", "0000111"})
    @DisplayName("Starts with zero and has odd length")
    void startsWithZeroAndHasOddLength(String stringThatStartsWithZeroAndHasOddLength) {
        assertMatch(stringThatStartsWithZeroAndHasOddLength);
    }

    @ParameterizedTest
    @ValueSource(strings = {"00", "0111", "000000"})
    @DisplayName("Starts with zero and has even length")
    void startsWithZeroAndHasEvenLength(String stringThatStartsWithZeroAndHasEvenLength) {
        assertNoMatch(stringThatStartsWithZeroAndHasEvenLength);
    }

    @ParameterizedTest
    @DisplayName("Starts with one and has odd length")
    @ValueSource(strings = {"1", "111", "10000"})
    void startsWithOneAndHasOddLength(String stringThatStartsWithOneAndHasOddLength) {
        assertNoMatch(stringThatStartsWithOneAndHasOddLength);
    }

    @ParameterizedTest
    @DisplayName("Starts with one and has even length")
    @ValueSource(strings = {"10", "100000", "10111011"})
    void startsWithOneAndHasEvenLength(String stringThatStartsWithOneAndHasEvenLength) {
        assertMatch(stringThatStartsWithOneAndHasEvenLength);
    }

    @Test
    @DisplayName("Empty string")
    void emptyString() {
        assertNoMatch("");
    }
}
