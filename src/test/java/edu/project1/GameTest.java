package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {
    private static class CallCountingUI implements UserInterface {
        private static int lossCount = 0;
        private static int victoryCount = 0;
        private static int correctCount = 0;
        private static int incorrectCount = 0;

        public static int getLossCount() {
            return lossCount;
        }

        public static int getVictoryCount() {
            return victoryCount;
        }

        public static int getCorrectCount() {
            return correctCount;
        }

        public static int getIncorrectCount() {
            return incorrectCount;
        }

        private final String guesses;
        private int index;

        private CallCountingUI(String guesses) {
            this.guesses = guesses;
            index = 0;
        }

        public static void resetCounters() {
            lossCount = victoryCount = correctCount = incorrectCount = 0;
        }

        @Override
        public char askForAGuess(String word, int i) {
            return guesses.charAt(index++);
        }

        @Override
        public void victoryMessage(String answer) {
            victoryCount++;
        }

        @Override
        public void lossMessage(String answer) {
            lossCount++;
        }

        @Override
        public void successfulGuessMessage() {
            correctCount++;
        }

        @Override
        public void unsuccessfulGuessMessage() {
            incorrectCount++;
        }
    }

    private void assertCalls(int expectedCorrect, int expectedIncorrect, int expectedVictories, int expectedLosses) {
        int actualCorrect = CallCountingUI.getCorrectCount();
        int actualIncorrect = CallCountingUI.getIncorrectCount();
        int actualVictories = CallCountingUI.getVictoryCount();
        int actualLosses = CallCountingUI.getLossCount();

        assertThat(actualCorrect).isEqualTo(expectedCorrect);
        assertThat(actualIncorrect).isEqualTo(expectedIncorrect);
        assertThat(actualVictories).isEqualTo(expectedVictories);
        assertThat(actualLosses).isEqualTo(expectedLosses);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1", "A", "bIbaboba"})
    @DisplayName("Game won't start on invalid word")
    void invalidWord(String invalidWord) {
        assertThrows(IllegalArgumentException.class, () -> new Game(() -> invalidWord, 1));
    }

    @Test
    @DisplayName("Game won't start on null word")
    void nullWord() {
        assertThrows(NullPointerException.class, () -> new Game(() -> null, 1));
    }

    @Test
    @DisplayName("Game won't start on null dictionary")
    void nullDictionary() {
        assertThrows(NullPointerException.class, () -> new Game(null, 1));
    }

    @Test
    @DisplayName("Game won't start on negative mistake count")
    void negativeMistakeCount() {
        assertThrows(IllegalArgumentException.class, () -> new Game(() -> "bibaboba", -1));
    }

    @Test
    @DisplayName("Incorrect guesses")
    void incorrectGuesses() {
        Game game = new Game(() -> "bbbb", 5, new CallCountingUI("aaaaaaa"));
        CallCountingUI.resetCounters();

        game.run();

        assertCalls(0, 6, 0,1);
    }

    @Test
    @DisplayName("Correct guesses")
    void correctGuesses() {
        Game game = new Game(() -> "abcd", 0, new CallCountingUI("abcd"));
        CallCountingUI.resetCounters();

        game.run();

        assertCalls(4, 0, 1, 0);
    }

    @Test
    @DisplayName("Give up")
    void giveUp() {
        Game game = new Game(() -> "bibaboba", 10, new CallCountingUI("#"));
        CallCountingUI.resetCounters();

        game.run();

        assertCalls(0, 0, 0, 1);
    }

    @Test
    @DisplayName("Mixed guesses")
    void mixedGuesses() {
        Game game = new Game(() -> "abcd", 5, new CallCountingUI("axecdfb"));
        CallCountingUI.resetCounters();

        game.run();

        assertCalls(4, 3, 1, 0);
    }
}
