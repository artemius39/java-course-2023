package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.Animals.pickHeaviestAnimals;
import static edu.hw4.TestUtils.withWeights;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PickHeaviestAnimalsTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        List<Animal> animals = List.of();

        List<Animal> picked = pickHeaviestAnimals(animals, 100);

        assertThat(picked).isEmpty();
    }

    static Stream<Arguments> collections() {
        List<Animal> animals = withWeights(12, 29, 3, 42, 128, 4);
        return TestUtils.collections(animals);
    }

    @ParameterizedTest
    @MethodSource("collections")
    @DisplayName("Pick more than in collection")
    void pickMoreThanInCollection(Collection<Animal> animals) {
        List<Animal> picked = pickHeaviestAnimals(animals, 10);

        assertThat(picked).containsExactlyElementsOf(withWeights(128, 42, 29, 12, 4, 3));
    }

    @ParameterizedTest
    @MethodSource("collections")
    @DisplayName("Pick zero elements")
    void pickZero(Collection<Animal> animals) {
        List<Animal> picked = pickHeaviestAnimals(animals, 0);

        assertThat(picked).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("Pick less than in collection")
    @MethodSource("collections")
    void pickLessThanInCollection(Collection<Animal> animals) {
        List<Animal> picked = pickHeaviestAnimals(animals, 3);

        assertThat(picked).containsExactlyElementsOf(withWeights(128, 42, 29));
    }

    @Test
    @DisplayName("Pick negative count")
    void pickNegativeCount() {
        assertThrows(IllegalArgumentException.class, () -> pickHeaviestAnimals(null, -1));
    }
}
