package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import static edu.hw4.Animals.sortByHeight;
import static edu.hw4.TestUtils.withHeight;
import static edu.hw4.TestUtils.withHeights;
import static org.assertj.core.api.Assertions.assertThat;

class SortByHeightTest {
    @Test
    @DisplayName("Empty collection")
    void emptyList() {
        List<Animal> animals = List.of();

        List<Animal> sorted = sortByHeight(animals);

        assertThat(sorted).isEmpty();
    }

    @Test
    @DisplayName("One element")
    void oneElement() {
        Set<Animal> animals = Set.of(withHeight(42));

        List<Animal> sorted = sortByHeight(animals);

        assertThat(sorted).containsExactly(withHeight(42));
    }

    static Stream<Arguments> variousCollections() {
        List<Animal> animals = withHeights(1, 3, 5, 6, 7, 10);
        return Stream.of(
                Arguments.of(animals),
                Arguments.of(Set.copyOf(animals)),
                Arguments.of(new ArrayDeque<>(animals)),
                Arguments.of(new HashSet<>(animals)),
                Arguments.of(new LinkedList<>(animals))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Various collections")
    void variousCollections(Collection<Animal> animals) {
        List<Animal> sorted = sortByHeight(animals);

        assertThat(sorted).containsExactlyElementsOf(withHeights(1, 3, 5, 6, 7, 10));
    }
}
