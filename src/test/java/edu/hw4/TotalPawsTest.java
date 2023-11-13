package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.Animals.totalPaws;
import static edu.hw4.TestUtils.collections;
import static edu.hw4.TestUtils.withType;
import static edu.hw4.TestUtils.withTypes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TotalPawsTest {
    private void test(Collection<Animal> animals, int expected) {
        int actual = totalPaws(animals);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        test(List.of(), 0);
    }

    private static Stream<Arguments> oneAnimal() {
        return Stream.of(Animal.Type.values()).map(Arguments::of);
    }

    @ParameterizedTest
    @DisplayName("One animal")
    @MethodSource
    void oneAnimal(Animal.Type type) {
        test(withTypes(type), withType(type).paws());
    }

    private static Stream<Arguments> multipleAnimals() {
        return TestUtils.collections(withTypes(
                Animal.Type.DOG,
                Animal.Type.CAT,
                Animal.Type.FISH,
                Animal.Type.DOG,
                Animal.Type.CAT,
                Animal.Type.DOG,
                Animal.Type.SPIDER,
                Animal.Type.BIRD,
                Animal.Type.SPIDER
        ));
    }

    @ParameterizedTest
    @DisplayName("Multiple animals")
    @MethodSource
    void multipleAnimals(Collection<Animal> animals) {
        test(animals, 38);
    }
}