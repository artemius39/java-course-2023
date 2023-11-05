package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import static edu.hw4.Animals.countByType;
import static edu.hw4.TestUtils.withTypes;
import static org.assertj.core.api.Assertions.assertThat;

class CountByTypeTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = Set.of();

        Map<Animal.Type, Integer> count = countByType(empty);

        assertThat(count)
                .containsKeys(Animal.Type.values())
                .allSatisfy((key, value) -> assertThat(value).isZero());
    }

    private static Stream<Arguments> someArePresent() {
        return TestUtils.collections(withTypes(
                Animal.Type.DOG,
                Animal.Type.FISH,
                Animal.Type.CAT,
                Animal.Type.DOG
        ));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Some are present")
    void someArePresent(Collection<Animal> animals) {
        Map<Animal.Type, Integer> count = countByType(animals);

        assertThat(count)
                .containsEntry(Animal.Type.DOG, 2)
                .containsEntry(Animal.Type.FISH, 1)
                .containsEntry(Animal.Type.CAT, 1)
                .containsEntry(Animal.Type.BIRD, 0)
                .containsEntry(Animal.Type.SPIDER, 0);
    }

    private static Stream<Arguments> allArePresent() {
        return TestUtils.collections(withTypes(
                Animal.Type.DOG,
                Animal.Type.DOG,
                Animal.Type.CAT,
                Animal.Type.SPIDER,
                Animal.Type.FISH,
                Animal.Type.CAT,
                Animal.Type.CAT,
                Animal.Type.BIRD,
                Animal.Type.SPIDER,
                Animal.Type.FISH,
                Animal.Type.BIRD,
                Animal.Type.BIRD
        ));
    }

    @ParameterizedTest
    @DisplayName("All are present")
    @MethodSource
    void allArePresent(Collection<Animal> animals) {
        Map<Animal.Type, Integer> count = countByType(animals);

        assertThat(count)
                .containsEntry(Animal.Type.DOG, 2)
                .containsEntry(Animal.Type.CAT, 3)
                .containsEntry(Animal.Type.SPIDER, 2)
                .containsEntry(Animal.Type.FISH, 2)
                .containsEntry(Animal.Type.BIRD, 3);
    }
}
