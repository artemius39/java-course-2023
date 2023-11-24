package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.Animals.weightIsGreaterThanHeight;
import static edu.hw4.TestUtils.withWeightAndHeight;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeightIsGreaterThanHeightTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = List.of();

        int actual = weightIsGreaterThanHeight(empty);

        assertThat(actual).isZero();
    }

    private static Stream<Arguments> noneMatch() {
        return TestUtils.collections(
                withWeightAndHeight(100, 1000),
                withWeightAndHeight(100, 101),
                withWeightAndHeight(10, 13),
                withWeightAndHeight(42, 1024)
        );
    }

    @ParameterizedTest
    @DisplayName("None match")
    @MethodSource
    void noneMatch(Collection<Animal> animals) {
        int actual = weightIsGreaterThanHeight(animals);

        assertThat(actual).isZero();
    }

    private static Stream<Arguments> someMatch() {
        return TestUtils.collections(
                withWeightAndHeight(100, 10),
                withWeightAndHeight(128, 16),
                withWeightAndHeight(256, 512),
                withWeightAndHeight(2, 4)
        );
    }
    @ParameterizedTest
    @DisplayName("Some match")
    @MethodSource
    void someMatch(Collection<Animal> animals) {
        int actual = weightIsGreaterThanHeight(animals);

        assertThat(actual).isEqualTo(2);
    }

    private static Stream<Arguments> allMatch() {
        return TestUtils.collections(
                withWeightAndHeight(4096, 32),
                withWeightAndHeight(128, 16),
                withWeightAndHeight(256, 8),
                withWeightAndHeight(2048, 4)
        );
    }

    @ParameterizedTest
    @DisplayName("All match")
    @MethodSource
    void allMatch(Collection<Animal> animals) {
        int actual = weightIsGreaterThanHeight(animals);

        assertThat(actual).isEqualTo(4);
    }

    private static Stream<Arguments> weightEqualsHeight() {
        return TestUtils.collections(
                withWeightAndHeight(64, 64)
        );
    }

    @ParameterizedTest
    @DisplayName("Weight equals height")
    @MethodSource
    void weightEqualsHeight(Collection<Animal> animals) {
        int actual = weightIsGreaterThanHeight(animals);

        assertThat(actual).isZero();
    }
}
