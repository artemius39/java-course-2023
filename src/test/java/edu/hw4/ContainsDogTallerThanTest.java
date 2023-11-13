package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;
import static edu.hw4.Animals.containsDogTallerThan;
import static edu.hw4.TestUtils.collections;
import static edu.hw4.TestUtils.withHeightAndType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContainsDogTallerThanTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = Set.of();

        boolean actual = containsDogTallerThan(empty, 42);

        assertThat(actual).isFalse();
    }

    private static Stream<Arguments> doesContain() {
        return collections(
                withHeightAndType(100, Animal.Type.DOG),
                withHeightAndType(10, Animal.Type.CAT),
                withHeightAndType(1, Animal.Type.SPIDER)
        );
    }

    @ParameterizedTest
    @DisplayName("Does contain")
    @MethodSource
    void doesContain(Collection<Animal> animals) {
        boolean actual = containsDogTallerThan(animals, 42);

        assertThat(actual).isTrue();
    }

    private static Stream<Arguments> doesNotContain() {
        return collections(
                withHeightAndType(100, Animal.Type.DOG),
                withHeightAndType(14, Animal.Type.FISH),
                withHeightAndType(3, Animal.Type.SPIDER)
        );
    }

    @ParameterizedTest
    @DisplayName("Does not contain")
    @MethodSource
    void doesNotContain(Collection<Animal> animals) {
        boolean actual = containsDogTallerThan(animals, 1024);

        assertThat(actual).isFalse();
    }

    private static Stream<Arguments> negativeHeight() {
        return collections(
                withHeightAndType(10, Animal.Type.DOG),
                withHeightAndType(42, Animal.Type.CAT)
        );
    }

    @ParameterizedTest
    @DisplayName("Negative height")
    @MethodSource
    void negativeHeight(Collection<Animal> animals) {
        boolean actual = containsDogTallerThan(animals, -42);

        assertThat(actual).isTrue();
    }

    private static Stream<Arguments> noDogs() {
        return collections(
                withHeightAndType(100, Animal.Type.SPIDER),
                withHeightAndType(1, Animal.Type.BIRD),
                withHeightAndType(4, Animal.Type.FISH)
        );
    }

    @ParameterizedTest
    @DisplayName("No dogs")
    @MethodSource
    void noDogs(Collection<Animal> animals) {
        boolean actual = containsDogTallerThan(animals, Integer.MIN_VALUE);

        assertThat(actual).isFalse();
    }

    private static Stream<Arguments> containsNonDogTallerThan() {
        return collections(
                withHeightAndType(12, Animal.Type.DOG),
                withHeightAndType(100, Animal.Type.FISH),
                withHeightAndType(100, Animal.Type.CAT),
                withHeightAndType(100, Animal.Type.SPIDER),
                withHeightAndType(100, Animal.Type.BIRD)
        );
    }

    @ParameterizedTest
    @DisplayName("Does contain but it's not a dog")
    @MethodSource
    void containsNonDogTallerThan(Collection<Animal> animals) {
        boolean actual = containsDogTallerThan(animals, 42);

        assertThat(actual).isFalse();
    }
}
