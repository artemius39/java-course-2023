package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.Animals.animalsThatBiteAndAreTallerThan100cm;
import static edu.hw4.TestUtils.withBitesAndHeight;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnimalsThatBiteAndAreTallerThan100cmTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = List.of();

        List<Animal> actual = animalsThatBiteAndAreTallerThan100cm(empty);

        assertThat(actual).isEmpty();
    }

    private static Stream<Arguments> nobodyBites() {
        return TestUtils.collections(List.of(
                withBitesAndHeight(false, 1),
                withBitesAndHeight(false, 100),
                withBitesAndHeight(false, 1000)
        ));
    }

    @ParameterizedTest
    @DisplayName("Nobody bites")
    @MethodSource
    void nobodyBites(Collection<Animal> animals) {
        List<Animal> actual = animalsThatBiteAndAreTallerThan100cm(animals);

        assertThat(actual).isEmpty();
    }

    private static Stream<Arguments> nobodyIsTallerThan100cm() {
        return TestUtils.collections(List.of(
                withBitesAndHeight(true, 10),
                withBitesAndHeight(true, 1),
                withBitesAndHeight(true, 42),
                withBitesAndHeight(true, 100)
        ));
    }

    @ParameterizedTest
    @DisplayName("Nobody is taller than 100 cm")
    @MethodSource
    void nobodyIsTallerThan100cm(Collection<Animal> animals) {
        List<Animal> actual = animalsThatBiteAndAreTallerThan100cm(animals);

        assertThat(actual).isEmpty();
    }

    private static Stream<Arguments> mixed() {
        return TestUtils.collections(List.of(
                withBitesAndHeight(true, 200),
                withBitesAndHeight(false, 256),
                withBitesAndHeight(true, 42),
                withBitesAndHeight(false, 128),
                withBitesAndHeight(true, 1024)
        ));
    }

    @ParameterizedTest
    @DisplayName("Mixed")
    @MethodSource
    void mixed(Collection<Animal> animals) {
        List<Animal> actual = animalsThatBiteAndAreTallerThan100cm(animals);

        assertThat(actual).containsExactly(
                withBitesAndHeight(true, 200),
                withBitesAndHeight(true, 1024)
        );
    }

    private static Stream<Arguments> exactly100cm() {
        return TestUtils.collections(withBitesAndHeight(true, 100));
    }

    @ParameterizedTest
    @DisplayName("Height of exactly 100 cm")
    @MethodSource
    void exactly100cm(Collection<Animal> animals) {
        List<Animal> actual = animalsThatBiteAndAreTallerThan100cm(animals);

        assertThat(actual).isEmpty();
    }
}
