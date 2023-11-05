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
import static edu.hw4.Animals.animalWithLongestName;
import static edu.hw4.TestUtils.withName;
import static edu.hw4.TestUtils.withNames;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnimalWithLongestNameTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        assertThrows(NoSuchElementException.class, () -> animalWithLongestName(List.of()));
    }

    private static Stream<Arguments> basicCase() {
        List<Animal> animals = withNames(
                "Garfield",
                "Snowball II",
                "Santa's Little Helper"
        );
        return TestUtils.collections(animals);
    }

    @ParameterizedTest
    @DisplayName("Basic case")
    @MethodSource
    void basicCase(Collection<Animal> animals) {
        Animal animalWithLongestName = animalWithLongestName(animals);

        assertThat(animalWithLongestName).isEqualTo(withName("Santa's Little Helper"));
    }

    private static Stream<Arguments> sameLengthNames() {
        List<Animal> animals = withNames(
                "Tom",
                "Jerry",
                "Spike"
        );
        return TestUtils.collections(animals);
    }

    @ParameterizedTest
    @DisplayName("Same length names")
    @MethodSource
    void sameLengthNames(Collection<Animal> animals) {
        Animal animalWithLongestName = animalWithLongestName(animals);

        assertThat(animalWithLongestName).isIn(withNames("Jerry", "Spike"));
    }
}
