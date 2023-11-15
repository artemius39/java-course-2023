package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.LENGTH_BETWEEN_ONE_AND_TWO;
import static org.assertj.core.api.Assertions.assertThat;

class LengthBetweenOneAndThreeTest {
    @Test
    @DisplayName("Length of 0")
    void lengthOf0() {
        assertThat("").doesNotMatch(LENGTH_BETWEEN_ONE_AND_TWO);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1"})
    @DisplayName("Length of 1")
    void lengthOf1(String stringOfLength1) {
        assertThat(stringOfLength1).matches(LENGTH_BETWEEN_ONE_AND_TWO);
    }

    @ParameterizedTest
    @DisplayName("Length of 2")
    @ValueSource(strings = {"00", "01", "10", "11"})
    void lengthOf2(String stringOfLength2) {
        assertThat(stringOfLength2).matches(LENGTH_BETWEEN_ONE_AND_TWO);
    }

    @ParameterizedTest
    @DisplayName("Length of 3")
    @ValueSource(strings = {"000", "001", "010", "100", "101", "110", "111"})
    void lengthOf3(String stringOfLength3) {
        assertThat(stringOfLength3).matches(LENGTH_BETWEEN_ONE_AND_TWO);
    }

    @ParameterizedTest
    @DisplayName("Length greater than 3")
    @ValueSource(strings = {"11111", "1010101", "1000", "0010", "1111111111111"})
    void lengthGreaterThan3(String stringOfLengthGreaterThan3) {
        assertThat(stringOfLengthGreaterThan3).doesNotMatch(LENGTH_BETWEEN_ONE_AND_TWO);
    }

    @Test
    @DisplayName("Invalid characters")
    void invalidCharacters() {
        assertThat("abc").doesNotMatch(LENGTH_BETWEEN_ONE_AND_TWO);
    }
}
