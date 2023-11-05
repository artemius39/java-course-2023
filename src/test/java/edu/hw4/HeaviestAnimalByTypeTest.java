package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static edu.hw4.Animals.heaviestAnimalByType;
import static edu.hw4.TestUtils.withTypeAndWeight;
import static edu.hw4.TestUtils.withTypeAndWeightAndName;
import static org.assertj.core.api.Assertions.assertThat;

class HeaviestAnimalByTypeTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = List.of();

        Map<Animal.Type, Animal> heaviestByType = heaviestAnimalByType(empty);

        assertThat(heaviestByType).isEmpty();
    }

    private static Stream<Arguments> allTypesArePresent() {
        List<Animal> animals = List.of(
                withTypeAndWeight(Animal.Type.DOG, 100),
                withTypeAndWeight(Animal.Type.CAT, 130),
                withTypeAndWeight(Animal.Type.FISH, 12),
                withTypeAndWeight(Animal.Type.SPIDER, 3),
                withTypeAndWeight(Animal.Type.DOG, 110),
                withTypeAndWeight(Animal.Type.CAT, 70),
                withTypeAndWeight(Animal.Type.SPIDER, 5),
                withTypeAndWeight(Animal.Type.BIRD, 1000),
                withTypeAndWeight(Animal.Type.BIRD, 20)
        );
        return TestUtils.collections(animals);
    }

    @ParameterizedTest
    @DisplayName("All types are present")
    @MethodSource
    void allTypesArePresent(Collection<Animal> animals) {
        Map<Animal.Type, Animal> heaviestByType = heaviestAnimalByType(animals);

        assertThat(heaviestByType)
                .containsEntry(Animal.Type.DOG, withTypeAndWeight(Animal.Type.DOG, 110))
                .containsEntry(Animal.Type.CAT, withTypeAndWeight(Animal.Type.CAT, 130))
                .containsEntry(Animal.Type.FISH, withTypeAndWeight(Animal.Type.FISH, 12))
                .containsEntry(Animal.Type.BIRD, withTypeAndWeight(Animal.Type.BIRD, 1000))
                .containsEntry(Animal.Type.SPIDER, withTypeAndWeight(Animal.Type.SPIDER, 5));
    }

    private static Stream<Arguments> someTypesAreMissing() {
        List<Animal> animals = List.of(
                withTypeAndWeight(Animal.Type.DOG, 200),
                withTypeAndWeight(Animal.Type.CAT, 30),
                withTypeAndWeight(Animal.Type.BIRD, 25),
                withTypeAndWeight(Animal.Type.CAT, 35),
                withTypeAndWeight(Animal.Type.DOG, 250)
        );
        return TestUtils.collections(animals);
    }

    @ParameterizedTest
    @DisplayName("Some types are missing")
    @MethodSource
    void someTypesAreMissing(Collection<Animal> animals) {
        Map<Animal.Type, Animal> heaviestByType = heaviestAnimalByType(animals);

        assertThat(heaviestByType)
                .containsEntry(Animal.Type.DOG, withTypeAndWeight(Animal.Type.DOG, 250))
                .containsEntry(Animal.Type.CAT, withTypeAndWeight(Animal.Type.CAT, 35))
                .containsEntry(Animal.Type.BIRD, withTypeAndWeight(Animal.Type.BIRD, 25))
                .doesNotContainKeys(Animal.Type.FISH, Animal.Type.SPIDER);
    }

    private static Stream<Arguments> sameWeight() {
        List<Animal> animals = List.of(
                withTypeAndWeight(Animal.Type.FISH, 42),
                withTypeAndWeightAndName(Animal.Type.CAT, 42, "Car"),
                withTypeAndWeightAndName(Animal.Type.CAT, 42, "Dog"),
                withTypeAndWeight(Animal.Type.DOG, 42)
        );
        return TestUtils.collections(animals);
    }

    @ParameterizedTest
    @DisplayName("Same weight")
    @MethodSource
    void sameWeight(Collection<Animal> animals) {
        Map<Animal.Type, Animal> heaviestByType = heaviestAnimalByType(animals);
        Animal heaviestCat = heaviestByType.get(Animal.Type.CAT);

        assertThat(heaviestByType)
                .containsEntry(Animal.Type.FISH, withTypeAndWeight(Animal.Type.FISH, 42))
                .containsEntry(Animal.Type.DOG, withTypeAndWeight(Animal.Type.DOG, 42))
                .containsKey(Animal.Type.CAT);
        assertThat(heaviestCat).isIn(
                withTypeAndWeightAndName(Animal.Type.CAT, 42, "Car"),
                withTypeAndWeightAndName(Animal.Type.CAT, 42, "Dog")
        );
    }
}
