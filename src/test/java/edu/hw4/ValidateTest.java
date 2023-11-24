package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import static edu.hw4.Animals.validate;
import static edu.hw4.TestUtils.collections;
import static org.assertj.core.api.Assertions.assertThat;

class ValidateTest {

    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = new ArrayList<>();

        Map<String, Set<ValidationError>> actual = validate(empty);

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
        Map<String, Set<ValidationError>> actual = validate(animals);

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
        Map<String, Set<ValidationError>> actual = validate(animals);

        assertThat(actual).containsKey("Bobik");
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
        Map<String, Set<ValidationError>> actual = validate(animals);

        assertThat(actual).containsKeys("", "    ");
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
        Map<String, Set<ValidationError>> actual = validate(animals);

        assertThat(actual).containsKey("ManBearPig");
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
        Map<String, Set<ValidationError>> actual = validate(animals);

        assertThat(actual).containsKey("Bobik");
    }

    private static Stream<Arguments> multipleErrors() {
        return collections(new Animal("NULL", null, null, 10, -10, 10, false));
    }

    @ParameterizedTest
    @DisplayName("Multiple errors")
    @MethodSource
    void multipleErrors(Collection<Animal> animals) {
        Map<String, Set<ValidationError>> actual = validate(animals);
        Set<ValidationError> errors = actual.get("NULL");

        assertThat(actual).containsKey("NULL");
        assertThat(errors).contains(
                new ValidationError("animal type cannot be null", "type"),
                new ValidationError("sex cannot be null", "sex"),
                new ValidationError("height must be positive", "height")
        );
    }

    private static Stream<Arguments> animalsWithTheSameName() {
        return collections(
                new Animal("Bibaboba", null, Animal.Sex.FEMALE, 1, 1, 1, true),
                new Animal("Bibaboba", Animal.Type.FISH, null, 1, 1, 1, true)
        );
    }

    @ParameterizedTest
    @DisplayName("Animals with the same name")
    @MethodSource
    void animalsWithTheSameName(Collection<Animal> animals) {
        Map<String, Set<ValidationError>> actual = validate(animals);
        Set<ValidationError> errors = actual.get("Bibaboba");

        assertThat(actual).containsKey("Bibaboba");
        assertThat(errors).contains(
                new ValidationError("animal type cannot be null", "type"),
                new ValidationError("sex cannot be null", "sex")
        );
    }
}
