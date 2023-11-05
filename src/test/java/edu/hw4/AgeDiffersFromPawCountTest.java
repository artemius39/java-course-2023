package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.Animals.ageDiffersFromPawCount;
import static edu.hw4.TestUtils.withAgeAndType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgeDiffersFromPawCountTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> animals = List.of();

        List<Animal> actual = ageDiffersFromPawCount(animals);

        assertThat(actual).isEmpty();
    }

    private static Stream<Arguments> someDiffer() {
        return TestUtils.collections(List.of(
                withAgeAndType(4, Animal.Type.CAT),
                withAgeAndType(10, Animal.Type.CAT),
                withAgeAndType(8, Animal.Type.SPIDER),
                withAgeAndType(2, Animal.Type.BIRD),
                withAgeAndType(0, Animal.Type.DOG)
        ));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Some differ")
    void someDiffer(Collection<Animal> animals) {
        List<Animal> actual = ageDiffersFromPawCount(animals);

        assertThat(actual).containsExactly(
                withAgeAndType(10, Animal.Type.CAT),
                withAgeAndType(0, Animal.Type.DOG)
        );
    }

    private static Stream<Arguments> noneDiffer() {
        return TestUtils.collections(List.of(
                withAgeAndType(4, Animal.Type.CAT),
                withAgeAndType(0, Animal.Type.FISH),
                withAgeAndType(8, Animal.Type.SPIDER)
        ));
    }

    @ParameterizedTest
    @DisplayName("None differ")
    @MethodSource
    void noneDiffer(Collection<Animal> animals) {
        List<Animal> actual = ageDiffersFromPawCount(animals);

        assertThat(actual).isEmpty();
    }

    private static Stream<Arguments> allDiffer() {
        return TestUtils.collections(List.of(
                withAgeAndType(3, Animal.Type.CAT),
                withAgeAndType(20, Animal.Type.DOG),
                withAgeAndType(100, Animal.Type.FISH),
                withAgeAndType(12, Animal.Type.BIRD),
                withAgeAndType(0, Animal.Type.SPIDER)
        ));
    }

    @ParameterizedTest
    @DisplayName("All differ")
    @MethodSource
    void allDiffer(Collection<Animal> animals) {
        List<Animal> actual = ageDiffersFromPawCount(animals);

        assertThat(actual).containsExactlyElementsOf(animals);
    }
}
