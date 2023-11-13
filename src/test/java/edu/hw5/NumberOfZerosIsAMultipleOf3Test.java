package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.NUMBER_OF_ZEROS_IS_A_MULTIPLE_OF_3;
import static org.assertj.core.api.Assertions.assertThat;

class NumberOfZerosIsAMultipleOf3Test {
    @ParameterizedTest
    @DisplayName("Number of zeros is a multiple of 3")
    @ValueSource(strings = {"", "1", "000", "10110111110", "100101000"})
    void numberOfZerosIsAMultipleOf3(String string) {
        assertThat(string).matches(NUMBER_OF_ZEROS_IS_A_MULTIPLE_OF_3);
    }

    @ParameterizedTest
    @DisplayName("Number of zeros is not a multiple of 3")
    @ValueSource(strings = {"0", "1011110", "00111011100"})
    void numberOfZerosIsNotAMultipleOf3(String string) {
        assertThat(string).doesNotMatch(NUMBER_OF_ZEROS_IS_A_MULTIPLE_OF_3);
    }
}
