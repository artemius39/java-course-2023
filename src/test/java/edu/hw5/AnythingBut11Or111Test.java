package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.ANYTHING_BUT_11_OR_111;
import static org.assertj.core.api.Assertions.assertThat;

class AnythingBut11Or111Test {
    @ParameterizedTest
    @DisplayName("11 and 111")
    @ValueSource(strings = {"11", "111"})
    void forbiddenStrings(String forbiddenString) {
        assertThat(forbiddenString).doesNotMatch(ANYTHING_BUT_11_OR_111);
    }

    @ParameterizedTest
    @DisplayName("Anything but 11 or 111")
    @ValueSource(strings = {"", "1", "0", "1111", "1010101010101101000"})
    void anythingBut11Or111(String string) {
        assertThat(string).matches(ANYTHING_BUT_11_OR_111);
    }
}
