package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.AT_LEAST_TWO_ZEROS_AND_NO_MORE_THAN_ONE_ONE;
import static org.assertj.core.api.Assertions.assertThat;

class AtLeastTwoZerosAndOneOneTest {
    @ParameterizedTest
    @DisplayName("Match")
    @ValueSource(strings = {"00", "1000", "010", "0000001", "000000000000", "100"})
    void match(String string) {
        assertThat(string).matches(AT_LEAST_TWO_ZEROS_AND_NO_MORE_THAN_ONE_ONE);
    }

    @ParameterizedTest
    @DisplayName("No match")
    @ValueSource(strings = {"1", "", "0", "1001", "10", "01"})
    void noMatch(String string) {
        assertThat(string).doesNotMatch(AT_LEAST_TWO_ZEROS_AND_NO_MORE_THAN_ONE_ONE);
    }
}
