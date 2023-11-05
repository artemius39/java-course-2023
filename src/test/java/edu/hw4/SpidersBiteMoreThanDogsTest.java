package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;
import static edu.hw4.Animals.spidersBiteMoreThanDogs;
import static edu.hw4.TestUtils.collections;
import static edu.hw4.TestUtils.withBitesAndType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpidersBiteMoreThanDogsTest {
    private static Stream<Arguments> noSpiders() {
        return collections(
                withBitesAndType(true, Animal.Type.DOG),
                withBitesAndType(true, Animal.Type.CAT),
                withBitesAndType(true, Animal.Type.CAT)
        );
    }

    @ParameterizedTest
    @DisplayName("No spiders")
    @MethodSource
    void noSpiders(Collection<Animal> animals) {
        boolean actual = spidersBiteMoreThanDogs(animals);

        assertThat(actual).isFalse();
    }

    private static Stream<Arguments> noDogs() {
        return collections(
                withBitesAndType(true, Animal.Type.SPIDER),
                withBitesAndType(true, Animal.Type.CAT),
                withBitesAndType(true, Animal.Type.CAT)
        );
    }
    @ParameterizedTest
    @DisplayName("No dogs")
    @MethodSource
    void noDogs(Collection<Animal> animals) {
        boolean actual = spidersBiteMoreThanDogs(animals);

        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = new LinkedList<>();

        boolean actual = spidersBiteMoreThanDogs(empty);

        assertThat(actual).isFalse();
    }

    private static Stream<Arguments> moreDogs() {
        return collections(
                withBitesAndType(true, Animal.Type.DOG),
                withBitesAndType(true, Animal.Type.DOG),
                withBitesAndType(true, Animal.Type.SPIDER),
                withBitesAndType(false, Animal.Type.SPIDER)
        );
    }

    @ParameterizedTest
    @DisplayName("More dogs")
    @MethodSource
    void moreDogs(Collection<Animal> animals) {
        boolean actual = spidersBiteMoreThanDogs(animals);

        assertThat(actual).isFalse();
    }

    private static Stream<Arguments> moreDogsButFrequencyIsLessThanSpiders() {
        return collections(
                withBitesAndType(true, Animal.Type.DOG),
                withBitesAndType(true, Animal.Type.DOG),
                withBitesAndType(false, Animal.Type.DOG),
                withBitesAndType(true, Animal.Type.SPIDER)
        );
    }

    @ParameterizedTest
    @DisplayName("More biting dogs but frequency is less than spiders'")
    @MethodSource
    void moreDogsButFrequencyIsLessThanSpiders(Collection<Animal> animals) {
        boolean actual = spidersBiteMoreThanDogs(animals);

        assertThat(actual).isTrue();
    }
}
