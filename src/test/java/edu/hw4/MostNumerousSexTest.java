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
import static edu.hw4.Animals.mostNumerousSex;
import static edu.hw4.TestUtils.withSexes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MostNumerousSexTest {
    private void test(Collection<Animal> animals, Animal.Sex expected) {
        Animal.Sex actual = mostNumerousSex(animals);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = List.of();

        assertThrows(NoSuchElementException.class, () -> mostNumerousSex(empty));
    }

    private static Stream<Arguments> moreMales() {
        return TestUtils.collections(withSexes(
                Animal.Sex.MALE,
                Animal.Sex.MALE,
                Animal.Sex.FEMALE,
                Animal.Sex.FEMALE,
                Animal.Sex.MALE,
                Animal.Sex.FEMALE,
                Animal.Sex.FEMALE,
                Animal.Sex.MALE,
                Animal.Sex.MALE
        ));
    }

    @ParameterizedTest
    @DisplayName("More males")
    @MethodSource
    void moreMales(Collection<Animal> animals) {
        test(animals, Animal.Sex.MALE);
    }

    private static Stream<Arguments> moreFemales() {
        return TestUtils.collections(withSexes(
                Animal.Sex.FEMALE,
                Animal.Sex.MALE,
                Animal.Sex.FEMALE,
                Animal.Sex.MALE,
                Animal.Sex.FEMALE,
                Animal.Sex.FEMALE,
                Animal.Sex.MALE
        ));
    }

    @ParameterizedTest
    @DisplayName("More females")
    @MethodSource
    void moreFemales(Collection<Animal> animals) {
        test(animals, Animal.Sex.FEMALE);
    }

    private static Stream<Arguments> noMales() {
        return TestUtils.collections(withSexes(
                Animal.Sex.FEMALE,
                Animal.Sex.FEMALE,
                Animal.Sex.FEMALE,
                Animal.Sex.FEMALE
        ));
    }

    @ParameterizedTest
    @DisplayName("No males")
    @MethodSource
    void noMales(Collection<Animal> animals) {
        test(animals, Animal.Sex.FEMALE);
    }

    private static Stream<Arguments> noFemales() {
        return TestUtils.collections(withSexes(
                Animal.Sex.MALE,
                Animal.Sex.MALE,
                Animal.Sex.MALE,
                Animal.Sex.MALE
        ));
    }

    @ParameterizedTest
    @DisplayName("No females")
    @MethodSource
    void noFemales(Collection<Animal> animals) {
        test(animals, Animal.Sex.MALE);
    }
}
