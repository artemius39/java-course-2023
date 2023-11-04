package edu.hw3.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static edu.hw3.task5.Task5.parseContacts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task5Test {
    private void test(SortOrder sortOrder, Contact[] expected, String... rawContacts) {
        Contact[] contacts = parseContacts(rawContacts, sortOrder);

        assertThat(contacts).isEqualTo(expected);
    }

    private void assertError(SortOrder sortOrder, String... rawContacts) {
        assertThrows(
                IllegalArgumentException.class,
                () -> parseContacts(rawContacts, sortOrder)
        );
    }

    private static Stream<Arguments> makeArguments(Contact... expectedAscending) {
        Contact[] expectedDescending = new Contact[expectedAscending.length];
        for (int i = 0; i < expectedDescending.length; i++) {
            expectedDescending[i] = expectedAscending[expectedAscending.length - i - 1];
        }

        return Stream.of(
                Arguments.of(SortOrder.ASCENDING, expectedAscending),
                Arguments.of(SortOrder.DESCENDING, expectedDescending)
        );
    }

    static Stream<Arguments> basicCase() {
        return makeArguments(
                new Contact("Thomas", "Aquinas"),
                new Contact("Rene", "Descartes"),
                new Contact("David", "Hume"),
                new Contact("John", "Locke")
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Basic case")
    void basicCase(SortOrder sortOrder, Contact[] expected) {
        test(sortOrder, expected, "Thomas Aquinas", "Rene Descartes", "David Hume", "John Locke");
    }

    static Stream<Arguments> sameFirstLetter() {
        return makeArguments(
                new Contact("Paul", "Erdos"),
                new Contact("Leonhard", "Euler"),
                new Contact("Carl", "Gauss")
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Same first letter")
    void sameFirstLetter(SortOrder sortOrder, Contact[] expected) {
        test(sortOrder, expected, "Paul Erdos", "Leonhard Euler", "Carl Gauss");
    }

    static Stream<Arguments> missingLastName() {
        return makeArguments(
                new Contact("Kyle", "Broflovski"),
                new Contact("Butters", null),
                new Contact("Eric", "Cartman"),
                new Contact("Stan", "Marsh")
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("No last name")
    void missingLastName(SortOrder sortOrder, Contact[] expected) {
        test(sortOrder, expected, "Stan Marsh", "Butters", "Eric Cartman", "Kyle Broflovski");
    }

    static Stream<Arguments> capsInTheMiddle() {
        return makeArguments(
                new Contact("Ian", "Mcbiggerson"),
                new Contact("Kenny", "McCormick")
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Capitals in the middle of the name")
    void capsInTheMiddle(SortOrder sortOrder, Contact[] expected) {
        test(sortOrder, expected, "Kenny McCormick", "Ian Mcbiggerson"); // McC < Mcb, but mcc > mcb
    }

    static Stream<Arguments> nonLettersInTheName() {
        return makeArguments(
                new Contact("Ash", "Baron-Cohen"),
                new Contact("Hsa", "Baroncohen")
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Non-letter characters in the name")
    void nonLettersInTheName(SortOrder sortOrder, Contact[] expected) {
        test(sortOrder, expected, "Ash Baron-Cohen", "Hsa Baroncohen");
    }

    @Test
    @DisplayName("Null sort order")
    void nullSorOrder() {
        assertThrows(
                NullPointerException.class,
                () -> parseContacts(new String[]{"Incontinentia Buttocks"}, null)
        );
    }

    static Stream<Arguments> sortOrders() {
        return Stream.of(Arguments.of(SortOrder.ASCENDING), Arguments.of(SortOrder.DESCENDING));
    }

    @ParameterizedTest
    @MethodSource("sortOrders")
    @DisplayName("Null contacts")
    void nullContacts(SortOrder sortOrder) {
        assertThat(parseContacts(null, sortOrder)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("sortOrders")
    @DisplayName("Empty contacts")
    void emptyArray(SortOrder sortOrder) {
        assertThat(parseContacts(new String[0], sortOrder)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("sortOrders")
    @DisplayName("Too long name")
    void nameTooLong(SortOrder sortOrder) {
        assertThrows(
                IllegalArgumentException.class,
                () -> parseContacts(new String[]{"Joey Jo-Jo Junior Shabadoo"}, sortOrder)
        );
    }

    @ParameterizedTest
    @MethodSource("sortOrders")
    @DisplayName("Null name")
    void nullName(SortOrder sortOrder) {
        assertThrows(
                NullPointerException.class,
                () -> parseContacts(new String[]{null}, sortOrder)
        );
    }

    @ParameterizedTest
    @MethodSource("sortOrders")
    @DisplayName("Superfluous spaces")
    void superfluousSpaces(SortOrder sortOrder) {
        assertError(sortOrder, "Naughtius  Maximus");
    }

    @ParameterizedTest
    @MethodSource("sortOrders")
    @DisplayName("Trailing space")
    void trailingSpace(SortOrder sortOrder) {
        assertError(sortOrder, "   Biggus Dickus     ");
    }

    @ParameterizedTest
    @MethodSource("sortOrders")
    @DisplayName("Empty name")
    void emptyName(SortOrder sortOrder) {
        assertError(sortOrder, "");
    }

    @ParameterizedTest
    @MethodSource("sortOrders")
    @DisplayName("Just space")
    void emptyFirstAndLastName(SortOrder sortOrder) {
        assertError(sortOrder, " ");
    }
}
