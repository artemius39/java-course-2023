package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import static edu.hw4.Animals.kthOldestAnimal;
import static edu.hw4.TestUtils.withAge;
import static edu.hw4.TestUtils.withAges;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KthOldestAnimalTest {
    private static Stream<Arguments> collections() {
        List<Animal> animals = withAges(10, 6, 3, 2, 7, 17);
        return TestUtils.collections(animals);
    }

    @ParameterizedTest
    @MethodSource("collections")
    @DisplayName("More than k elements")
    void moreThanKElements(Collection<Animal> animals) {
        Animal animal = kthOldestAnimal(animals, 4);

        assertThat(animal).isEqualTo(withAge(6));
    }

    @ParameterizedTest
    @DisplayName("Less than k elements")
    @MethodSource("collections")
    void lessThanKElements(Collection<Animal> animals) {
        assertThrows(NoSuchElementException.class, () -> kthOldestAnimal(animals, 7));
    }

    @ParameterizedTest
    @DisplayName("Exactly k elements")
    @MethodSource("collections")
    void exactlyKElements(Collection<Animal> animals) {
        Animal animal = kthOldestAnimal(animals, 6);

        assertThat(animal).isEqualTo(withAge(2));
    }

    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> animals = List.of();

        assertThrows(NoSuchElementException.class, () -> kthOldestAnimal(animals, 1));
    }
}
