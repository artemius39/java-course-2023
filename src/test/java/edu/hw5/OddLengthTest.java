package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.ODD_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;

class OddLengthTest {
    @ParameterizedTest
    @DisplayName("Odd length")
    @ValueSource(strings = {"1", "0", "100", "01000", "1111111"})
    void oddLength(String stringOfOddLength) {
        assertThat(stringOfOddLength).matches(ODD_LENGTH);
    }

    @ParameterizedTest
    @DisplayName("Even length")
    @ValueSource(strings = {"", "00", "1111", "1001", "111000"})
    void evenLength(String evenLengthString) {
        assertThat(evenLengthString).doesNotMatch(ODD_LENGTH);
    }
}
