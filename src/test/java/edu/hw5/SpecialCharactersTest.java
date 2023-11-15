package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.SPECIAL_CHARACTERS;
import static org.assertj.core.api.Assertions.assertThat;

class SpecialCharactersTest {
    @ParameterizedTest
    @DisplayName("One character")
    @ValueSource(strings = {"~", "!", "@", "#", "$", "%", "^", "&", "*", "|"})
    void oneCharacter(String s) {
        assertThat(s).matches(SPECIAL_CHARACTERS);
    }

    @ParameterizedTest
    @DisplayName("Multiple characters")
    @ValueSource(strings = {"abc~cdef!@123", "###aefaef^^", "fisting is $300", "||||||", "*~*"})
    void multipleCharacters(String s) {
        assertThat(s).matches(SPECIAL_CHARACTERS);
    }

    @Test
    @DisplayName("Password doesn't contain special characters")
    void passwordDoesntContainSpecialCharacters() {
        assertThat("qwerty").doesNotMatch(SPECIAL_CHARACTERS);
    }

    @Test
    @DisplayName("Empty string")
    void emptyString() {
        assertThat("").doesNotMatch(SPECIAL_CHARACTERS);
    }
}
