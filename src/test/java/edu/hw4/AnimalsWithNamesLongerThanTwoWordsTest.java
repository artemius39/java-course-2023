package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.Animals.animalsWithNamesLongerThanTwoWords;
import static edu.hw4.TestUtils.collections;
import static edu.hw4.TestUtils.withName;
import static edu.hw4.TestUtils.withNames;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnimalsWithNamesLongerThanTwoWordsTest {
    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Collection<Animal> empty = List.of();

        List<Animal> actual = animalsWithNamesLongerThanTwoWords(empty);

        assertThat(actual).isEmpty();
    }

    private static Stream<Arguments> nameIsShorter() {
        return TestUtils.collections(withNames(
                "Nibbler", "Uncle Shnuck"
        ));
    }

    @ParameterizedTest
    @DisplayName("Name is shorter")
    @MethodSource
    void nameIsShorter(Collection<Animal> animals) {
        List<Animal> actual = animalsWithNamesLongerThanTwoWords(animals);

        assertThat(actual).isEmpty();
    }

    private static Stream<Arguments> nameIsLonger() {
        return TestUtils.collections(withNames("Field Marshal Windbag"));
    }

    @ParameterizedTest
    @DisplayName("Name is longer")
    @MethodSource
    void nameIsLonger(Collection<Animal> animals) {
        List<Animal> actual = animalsWithNamesLongerThanTwoWords(animals);

        assertThat(actual).containsExactlyElementsOf(animals);
    }

    private static Stream<Arguments> mixed() {
        return TestUtils.collections(withNames(
                "Seymour", "Joey Jo-Jo Jr. Shabadoo", "Roach", "Three Word Name"
        ));
    }

    @ParameterizedTest
    @DisplayName("Mixed")
    @MethodSource
    void mixed(Collection<Animal> animals) {
        List<Animal> actual = animalsWithNamesLongerThanTwoWords(animals);

        assertThat(actual).containsExactlyElementsOf(withNames(
                "Joey Jo-Jo Jr. Shabadoo", "Three Word Name"
        ));
    }

    private static Stream<Arguments> trailingWhitespace() {
        return TestUtils.collections(withNames(
                "   Barbas      ", "    I am     five      words         long       "
        ));
    }

    @ParameterizedTest
    @DisplayName("Trailing whitespace")
    @MethodSource
    void trailingWhitespace(Collection<Animal> animals) {
        List<Animal> actual = animalsWithNamesLongerThanTwoWords(animals);

        assertThat(actual).containsExactly(withName("    I am     five      words         long       "));
    }

    private static Stream<Arguments> emptyName() {
        return collections(withName(""));
    }

    @ParameterizedTest
    @DisplayName("Empty name")
    @MethodSource
    void emptyName(Collection<Animal> animals) {
        List<Animal> actual = animalsWithNamesLongerThanTwoWords(animals);

        assertThat(actual).isEmpty();
    }
}
