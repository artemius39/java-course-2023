package edu.hw3.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static edu.hw3.task3.Task3.freqDict;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task3Test {
    @Test
    @DisplayName("Null list")
    void nullListThrowsNPE() {
        assertThrows(NullPointerException.class, () -> freqDict(null));
    }

    @Test
    @DisplayName("Empty list")
    void emptyArrayYieldsEmptyMap() {
        assertThat(freqDict(List.of())).isEqualTo(Map.<Object, Integer>of());
    }

    @Test
    @DisplayName("Strings")
    void strings() {
        assertThat(freqDict(List.of("a", "bb", "a", "bb"))).isEqualTo(Map.of("a", 2, "bb", 2));
    }

    static Stream<Arguments> differentTypes() {
        return Stream.of(
                Arguments.of(
                        List.of(1, 1, 3, 1, 2, 2),
                        Map.of(
                                1, 3,
                                2, 2,
                                3, 1
                        )
                ),
                Arguments.of(
                        List.of(0.1, Math.PI, Math.E, 0.1, 0.2, Math.E, Math.E),
                        Map.of(
                                0.1, 2,
                                Math.PI, 1,
                                Math.E, 3,
                                0.2, 1
                        )
                ),
                Arguments.of(
                        List.of(List.of(), List.of(1, 2, 3), List.of(1, 2, 3)),
                        Map.of(
                                List.of(), 1,
                                List.of(1, 2, 3), 2
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Different types")
    <E> void differentTypes(List<E> elements, Map<E, Integer> expected) {
        assertThat(freqDict(elements)).isEqualTo(expected);
    }
}
