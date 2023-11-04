package edu.hw3.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static edu.hw3.task2.Task2.clusterize;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task2Test {
    private void test(String parentheses, List<String> expected) {
        List<String> clusterized = clusterize(parentheses);

        assertThat(clusterized).isEqualTo(expected);
    }

    @Test
    @DisplayName("Null string")
    void nullString() {
        assertThrows(NullPointerException.class, () -> clusterize(null));
    }

    @Test
    @DisplayName("Empty string")
    void emptyString() {
        test("", List.of());
    }

    @ParameterizedTest
    @ValueSource(strings = {"(", ")", "())", "(()"})
    @DisplayName("Unbalanced string")
    void unbalancedString(String unbalancedString) {
        assertThrows(IllegalArgumentException.class, () -> clusterize(unbalancedString));
    }

    @ParameterizedTest
    @ValueSource(strings = {"#", "((x))", "\r", "()1"})
    @DisplayName("Invalid character")
    void invalidCharacters(String stringWithInvalidCharacters) {
        assertThrows(IllegalArgumentException.class, () -> clusterize(stringWithInvalidCharacters));
    }

    static Stream<Arguments> goodScenarios() {
        return Stream.of(
                Arguments.of("()()()", List.of("()", "()", "()")),
                Arguments.of("((()))", List.of("((()))")),
                Arguments.of("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())")),
                Arguments.of("((())())(()(()()))", List.of("((())())", "(()(()()))"))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Good scenarios")
    void goodScenarios(String parentheses, List<String> expected) {
        test(parentheses, expected);
    }
}
