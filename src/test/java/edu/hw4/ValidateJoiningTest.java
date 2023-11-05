package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;
import static edu.hw4.Animals.validateJoining;
import static edu.hw4.TestUtils.collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateJoiningTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = new ArrayList<>();

        Map<String, String> actual = validateJoining(empty);

        assertThat(actual).isEmpty();
    }

    private static Stream<Arguments> noErrors() {
        return collections(new Animal(
                "Abobus",
                Animal.Type.CAT,
                Animal.Sex.MALE,
                12, 42, 5,
                true
        ));
    }

    @ParameterizedTest
    @DisplayName("No errors")
    @MethodSource
    void noErrors(Collection<Animal> animals) {
        Map<String, String> actual = validateJoining(animals);

        assertThat(actual).isEmpty();
    }

    private static Stream<Arguments> negativeAge() {
        return collections(
                new Animal("Bobik", Animal.Type.DOG, Animal.Sex.MALE, -42, 1, 1, true)
        );
    }

    @ParameterizedTest
    @DisplayName("Negative age")
    @MethodSource
    void negativeAge(Collection<Animal> animals) {
        Map<String, String> actual = validateJoining(animals);

        assertThat(actual).containsEntry("Bobik", "age");
    }

    private static Stream<Arguments> nullName() {
        return collections(
                new Animal(null, Animal.Type.DOG, Animal.Sex.MALE, 1, 1, 1, true)
        );
    }

    @ParameterizedTest
    @DisplayName("Null name")
    @MethodSource
    void nullName(Collection<Animal> animals) {
        Map<String, String> actual = validateJoining(animals);

        assertThat(actual).containsEntry(null, "name");
    }

    private static Stream<Arguments> blankName() {
        return collections(
                new Animal("", Animal.Type.DOG, Animal.Sex.MALE, 42, 1, 1, true),
                new Animal("    ", Animal.Type.DOG, Animal.Sex.MALE, 42, 1, 1, true)
        );
    }

    @ParameterizedTest
    @DisplayName("Blank name")
    @MethodSource
    void blankName(Collection<Animal> animals) {
        Map<String, String> actual = validateJoining(animals);

        assertThat(actual)
                .containsEntry("", "name")
                .containsEntry("    ", "name");
    }

    private static Stream<Arguments> nullType() {
        return collections(
                new Animal("ManBearPig", null, Animal.Sex.MALE, 42, 1, 1, true)
        );
    }

    @ParameterizedTest
    @DisplayName("Null type")
    @MethodSource
    void nullType(Collection<Animal> animals) {
        Map<String, String> actual = validateJoining(animals);

        assertThat(actual).containsEntry("ManBearPig", "type");
    }

    private static Stream<Arguments> negativeHeight() {
        return collections(
                new Animal("Bobik", Animal.Type.DOG, Animal.Sex.MALE, 1, -42, 1, true)
        );
    }

    @ParameterizedTest
    @DisplayName("Negative height")
    @MethodSource
    void negativeHeight(Collection<Animal> animals) {
        Map<String, String> actual = validateJoining(animals);

        assertThat(actual).containsEntry("Bobik", "height");
    }

    private static Stream<Arguments> multipleErrors() {
        return collections(new Animal("NULL", null, null, 10, -10, 10, false));
    }

    @ParameterizedTest
    @DisplayName("Multiple errors")
    @MethodSource
    void multipleErrors(Collection<Animal> animals) {
        Map<String, String> actual = validateJoining(animals);
        String errors = actual.get("NULL");

        assertThat(actual).containsKey("NULL");
        assertThat(errors).isIn(
                "type, sex, height",
                "type, height, sex",
                "sex, type, height",
                "sex, height, type",
                "height, sex, type",
                "height, type, sex"
        );
    }
}
