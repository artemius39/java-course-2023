package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.Animals.sortByTypeSexName;
import static edu.hw4.TestUtils.collections;
import static edu.hw4.TestUtils.withTypeAndSex;
import static edu.hw4.TestUtils.withTypeAndSexAndName;
import static edu.hw4.TestUtils.withTypes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SortByTypeSexNameTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = new ArrayDeque<>();

        List<Animal> actual = sortByTypeSexName(empty);

        assertThat(actual).isEmpty();
    }

    private static Stream<Arguments> sortByType() {
        return TestUtils.collections(withTypes(
                Animal.Type.DOG,
                Animal.Type.CAT,
                Animal.Type.SPIDER
        ));
    }

    @ParameterizedTest
    @DisplayName("Sort by type")
    @MethodSource
    void sortByType(Collection<Animal> animals) {
        List<Animal> actual = sortByTypeSexName(animals);

        assertThat(actual).containsExactlyElementsOf(withTypes(
                Animal.Type.CAT,
                Animal.Type.DOG,
                Animal.Type.SPIDER
        ));
    }

    private static Stream<Arguments> sortByTypeSex() {
        return collections(
                TestUtils.withTypeAndSex(Animal.Type.CAT, Animal.Sex.MALE),
                TestUtils.withTypeAndSex(Animal.Type.CAT, Animal.Sex.FEMALE),
                TestUtils.withTypeAndSex(Animal.Type.DOG, Animal.Sex.FEMALE),
                TestUtils.withTypeAndSex(Animal.Type.BIRD, Animal.Sex.MALE)
        );
    }

    @ParameterizedTest
    @DisplayName("Sort by type and sex")
    @MethodSource
    void sortByTypeSex(Collection<Animal> animals) {
        List<Animal> actual = sortByTypeSexName(animals);

        assertThat(actual).containsExactly(
                withTypeAndSex(Animal.Type.CAT, Animal.Sex.MALE),
                withTypeAndSex(Animal.Type.CAT, Animal.Sex.FEMALE),
                withTypeAndSex(Animal.Type.DOG, Animal.Sex.FEMALE),
                withTypeAndSex(Animal.Type.BIRD, Animal.Sex.MALE)
        );
    }

    private static Stream<Arguments> sortByTypeSexAndName() {
        return collections(
                withTypeAndSexAndName(Animal.Type.CAT, Animal.Sex.MALE, "Zebra"),
                withTypeAndSexAndName(Animal.Type.CAT, Animal.Sex.MALE, "Bibaboba"),
                withTypeAndSexAndName(Animal.Type.BIRD, Animal.Sex.MALE, "Aaaaa")
        );
    }

    @ParameterizedTest
    @DisplayName("Sort by type and sex and name")
    @MethodSource
    void sortByTypeSexAndName(Collection<Animal> animals) {
        List<Animal> actual = sortByTypeSexName(animals);

        assertThat(actual).containsExactly(
                withTypeAndSexAndName(Animal.Type.CAT, Animal.Sex.MALE, "Bibaboba"),
                withTypeAndSexAndName(Animal.Type.CAT, Animal.Sex.MALE, "Zebra"),
                withTypeAndSexAndName(Animal.Type.BIRD, Animal.Sex.MALE, "Aaaaa")
        );
    }
}
