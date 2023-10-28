package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleInterfaceTest {
    private char getGuess(UserInterface ui) {
        return ui.askForAGuess("", 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab", "1", "", " ", "A", "\n\n\n"})
    @DisplayName("Forgive invalid input")
    void retryOnInvalidInput(String invalidInput) {
        UserInterface ui = new ConsoleInterface(new Scanner(invalidInput + "\na\n"));

        char guess = getGuess(ui);

        assertThat(guess).isEqualTo('a');
    }

    @Test
    @DisplayName("Give up on command")
    void giveUpOnCommand() {
        UserInterface ui = new ConsoleInterface(new Scanner("give up"));

        char guess = getGuess(ui);

        assertThat(guess).isEqualTo(UserInterface.GIVE_UP_CHAR);
    }

    @Test
    @DisplayName("Give up on eof")
    void giveUpOnEOF() {
        UserInterface ui = new ConsoleInterface(new Scanner(""));

        char guess = getGuess(ui);

        assertThat(guess).isEqualTo(UserInterface.GIVE_UP_CHAR);
    }
}