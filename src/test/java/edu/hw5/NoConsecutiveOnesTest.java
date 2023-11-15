package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.NO_CONSECUTIVE_ONES;
import static org.assertj.core.api.Assertions.assertThat;

class NoConsecutiveOnesTest {
    @ParameterizedTest
    @DisplayName("Match")
    @ValueSource(strings = {"1", "101", "0101", "00100001", "10001", "1001"})
    void match(String string) {
        assertThat(string).matches(NO_CONSECUTIVE_ONES);
    }

    @ParameterizedTest
    @DisplayName("No match")
    @ValueSource(strings = {"11", "0110", "1100", "100011", "11111", "0011"})
    void noMatch(String string) {
        assertThat(string).doesNotMatch(NO_CONSECUTIVE_ONES);
    }
}
