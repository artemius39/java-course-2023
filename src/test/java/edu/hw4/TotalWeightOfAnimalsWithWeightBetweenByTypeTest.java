package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;
import static edu.hw4.Animals.totalWeightOfAnimalsWithWeightBetweenByType;
import static edu.hw4.TestUtils.collections;
import static edu.hw4.TestUtils.withTypeAndWeight;
import static org.assertj.core.api.Assertions.assertThat;

class TotalWeightOfAnimalsWithWeightBetweenByTypeTest {
    private void assertValues(Map<Animal.Type, Integer> actual, int expectedDog, int expectedCat, int expectedSpider,
                              int expectedBird, int expectedFish) {
        assertThat(actual)
                .containsEntry(Animal.Type.DOG, expectedDog)
                .containsEntry(Animal.Type.CAT, expectedCat)
                .containsEntry(Animal.Type.SPIDER, expectedSpider)
                .containsEntry(Animal.Type.BIRD, expectedBird)
                .containsEntry(Animal.Type.FISH, expectedFish);
    }

    private void assertZeros(Map<Animal.Type, Integer> actual) {
        assertValues(actual, 0, 0, 0, 0, 0);
    }

    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = new HashSet<>();

        Map<Animal.Type, Integer> actual = totalWeightOfAnimalsWithWeightBetweenByType(
                empty, Integer.MIN_VALUE, Integer.MAX_VALUE);

        assertZeros(actual);
    }

    private static Stream<Arguments> weightLowerThanBound() {
        return collections(
                withTypeAndWeight(Animal.Type.DOG, 10),
                withTypeAndWeight(Animal.Type.CAT, 5),
                withTypeAndWeight(Animal.Type.BIRD, 1)
        );
    }

    @ParameterizedTest
    @DisplayName("Weight lower than lower bound")
    @MethodSource
    void weightLowerThanBound(Collection<Animal> animals) {
        Map<Animal.Type, Integer> actual = totalWeightOfAnimalsWithWeightBetweenByType(
                animals, 100, 1000);

        assertZeros(actual);
    }

    private static Stream<Arguments> weightGreaterThanUpperBound() {
        return collections(
                withTypeAndWeight(Animal.Type.DOG, 1024),
                withTypeAndWeight(Animal.Type.CAT, 256),
                withTypeAndWeight(Animal.Type.FISH, 4096),
                withTypeAndWeight(Animal.Type.SPIDER, 512)
        );
    }

    @ParameterizedTest
    @DisplayName("Weight greater than upper bound")
    @MethodSource
    void weightGreaterThanUpperBound(Collection<Animal> animals) {
        Map<Animal.Type, Integer> actual = totalWeightOfAnimalsWithWeightBetweenByType(
                animals, 10, 100);

        assertZeros(actual);
    }

    private static Stream<Arguments> weightWithinBounds() {
        return collections(
                withTypeAndWeight(Animal.Type.DOG, 32),
                withTypeAndWeight(Animal.Type.CAT, 16),
                withTypeAndWeight(Animal.Type.SPIDER, 64)
        );
    }

    @ParameterizedTest
    @DisplayName("Weight within bounds")
    @MethodSource
    void weightWithinBounds(Collection<Animal> animals) {
        Map<Animal.Type, Integer> actual = totalWeightOfAnimalsWithWeightBetweenByType(
                animals, 10, 100);

        assertValues(actual, 32, 16, 64, 0, 0);
    }

    private static Stream<Arguments> weightExactlyAtLowerBound() {
        return collections(
                withTypeAndWeight(Animal.Type.BIRD, 100),
                withTypeAndWeight(Animal.Type.BIRD, 99)
        );
    }

    @ParameterizedTest
    @DisplayName("Weight exactly at lower bound")
    @MethodSource
    void weightExactlyAtLowerBound(Collection<Animal> animals) {
        Map<Animal.Type, Integer> actual = totalWeightOfAnimalsWithWeightBetweenByType(
                animals, 100, 1000);

        assertValues(actual, 0, 0, 0, 100, 0);
    }

    private static Stream<Arguments> weightExactlyAtUpperBound() {
        return collections(
                withTypeAndWeight(Animal.Type.FISH, 100),
                withTypeAndWeight(Animal.Type.FISH, 101)
        );
    }

    @ParameterizedTest
    @DisplayName("Weight exactly at upper bound")
    @MethodSource
    void weightExactlyAtUpperBound(Collection<Animal> animals) {
        Map<Animal.Type, Integer> actual = totalWeightOfAnimalsWithWeightBetweenByType(animals, 10, 100);

        assertValues(actual, 0, 0, 0, 0, 100);
    }

    private static Stream<Arguments> summation() {
        return collections(
                withTypeAndWeight(Animal.Type.DOG, 1),
                withTypeAndWeight(Animal.Type.CAT, 3),
                withTypeAndWeight(Animal.Type.FISH, 100),
                withTypeAndWeight(Animal.Type.DOG, 127),
                withTypeAndWeight(Animal.Type.CAT, 32),
                withTypeAndWeight(Animal.Type.CAT, 256),
                withTypeAndWeight(Animal.Type.DOG, 512),
                withTypeAndWeight(Animal.Type.DOG, 10000),
                withTypeAndWeight(Animal.Type.FISH, 300)
        );
    }

    @ParameterizedTest
    @DisplayName("Weights get summed")
    @MethodSource
    void summation(Collection<Animal> animals) {
        Map<Animal.Type, Integer> actual = totalWeightOfAnimalsWithWeightBetweenByType(
                animals, 10, 1024);

        assertValues(actual, 127 + 512, 32 + 256, 0, 0, 100 + 300);
    }

    private static Stream<Arguments> upperBoundEqualsLowerBound() {
        return collections(
                withTypeAndWeight(Animal.Type.DOG, 101),
                withTypeAndWeight(Animal.Type.DOG, 100),
                withTypeAndWeight(Animal.Type.DOG, 99)
        );
    }

    @ParameterizedTest
    @DisplayName("Upper bound = lower bound")
    @MethodSource
    void upperBoundEqualsLowerBound(Collection<Animal> animals) {
        Map<Animal.Type, Integer> actual = totalWeightOfAnimalsWithWeightBetweenByType(
                animals, 100, 100);

        assertValues(actual, 100, 0, 0, 0, 0);
    }

    private static Stream<Arguments> upperBoundIsLessThanLowerBound() {
        return collections(
                withTypeAndWeight(Animal.Type.DOG, 100),
                withTypeAndWeight(Animal.Type.CAT, 1000),
                withTypeAndWeight(Animal.Type.SPIDER, 42)
        );
    }

    @ParameterizedTest
    @DisplayName("Upper bound < Lower bound")
    @MethodSource
    void upperBoundIsLessThanLowerBound(Collection<Animal> animals) {
        Map<Animal.Type, Integer> actual = totalWeightOfAnimalsWithWeightBetweenByType(
                animals, 1000, 100);

        assertZeros(actual);
    }
}
