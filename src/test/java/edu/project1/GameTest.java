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
        private static int alreadyGuessedCount = 0;
        private static int alreadyFailedCount = 0;
        private static int answerRevealCount = 0;

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

        public static int getAlreadyGuessedCount() {
            return alreadyGuessedCount;
        }

        public static int getAlreadyFailedCount() {
            return alreadyFailedCount;
        }

        public static int getAnswerRevealCount() {
            return answerRevealCount;
        }

        private final String guesses;
        private int index;

        private CallCountingUI(String guesses) {
            this.guesses = guesses + GIVE_UP_CHAR;
            index = 0;
        }

        public static void resetCounters() {
            answerRevealCount = lossCount = victoryCount = correctCount = incorrectCount = alreadyFailedCount = alreadyGuessedCount = 0;
        }

        @Override
        public void alreadyGuessedMessage() {
            alreadyGuessedCount++;
        }

        @Override
        public void alreadyFailedMessage() {
            alreadyFailedCount++;
        }

        @Override
        public char askForAGuess(String word, int i) {
            return guesses.charAt(index++);
        }

        @Override
        public void victoryMessage() {
            victoryCount++;
        }

        @Override
        public void lossMessage() {
            lossCount++;
        }

        @Override
        public void revealAnswer(String answer) {
            answerRevealCount++;
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

    private void assertCalls(int expectedCorrect, int expectedIncorrect, int expectedAlreadyGuessed, int expectedAlreadyFailed, int expectedVictories, int expectedLosses) {
        int actualCorrect = CallCountingUI.getCorrectCount();
        int actualIncorrect = CallCountingUI.getIncorrectCount();
        int actualAlreadyGuessed = CallCountingUI.getAlreadyGuessedCount();
        int actualAlreadyFailed = CallCountingUI.getAlreadyFailedCount();
        int actualVictories = CallCountingUI.getVictoryCount();
        int actualLosses = CallCountingUI.getLossCount();

        assertThat(actualCorrect).isEqualTo(expectedCorrect);
        assertThat(actualIncorrect).isEqualTo(expectedIncorrect);
        assertThat(actualAlreadyGuessed).isEqualTo(expectedAlreadyGuessed);
        assertThat(actualAlreadyFailed).isEqualTo(expectedAlreadyFailed);
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
        Game game = new Game(() -> "bbbb", 5, new CallCountingUI("acdefghijkl"));
        CallCountingUI.resetCounters();

        game.run();

        assertCalls(0, 6, 0, 0, 0,1);
    }

    @Test
    @DisplayName("Correct guesses")
    void correctGuesses() {
        Game game = new Game(() -> "abcd", 0, new CallCountingUI("abcd"));
        CallCountingUI.resetCounters();

        game.run();

        assertCalls(4, 0, 0, 0, 1, 0);
    }

    @Test
    @DisplayName("Give up")
    void giveUp() {
        Game game = new Game(() -> "bibaboba", 10, new CallCountingUI("#"));
        CallCountingUI.resetCounters();

        game.run();

        assertCalls(0, 0, 0, 0, 0, 1);
    }

    @Test
    @DisplayName("Mixed guesses")
    void mixedGuesses() {
        Game game = new Game(() -> "abcd", 5, new CallCountingUI("axecdfb"));
        CallCountingUI.resetCounters();

        game.run();

        assertCalls(4, 3, 0, 0, 1, 0);
    }

    @Test
    @DisplayName("Answer is revealed on victory")
    void answerRevealOnVictory() {
        Game game = new Game(() -> "a", 0, new CallCountingUI("a"));
        CallCountingUI.resetCounters();

        game.run();
        int answerReveals = CallCountingUI.getAnswerRevealCount();

        assertThat(answerReveals).isOne();
    }

    @Test
    @DisplayName("Answer is revealed on loss")
    void answerRevealOnLoss() {
        Game game = new Game(() -> "a", 0, new CallCountingUI("b"));
        CallCountingUI.resetCounters();

        game.run();
        int answerReveals = CallCountingUI.getAnswerRevealCount();

        assertThat(answerReveals).isOne();
    }

    @Test
    @DisplayName("Answer is revealed on give up")
    void answerRevealOnGiveUp() {
        Game game = new Game(() -> "a", 0, new CallCountingUI("#"));
        CallCountingUI.resetCounters();

        game.run();
        int answerReveals = CallCountingUI.getAnswerRevealCount();

        assertThat(answerReveals).isOne();
    }

    @Test
    @DisplayName("Trying to guess already guessed letter")
    void tryingToGuessAlreadyGuessedLetter() {
        Game game = new Game(() -> "abcd", 5, new CallCountingUI("aaaa"));
        CallCountingUI.resetCounters();

        game.run();

        assertCalls(1, 0, 3, 0, 0, 1);
    }

    @Test
    @DisplayName("Trying to guess already failed letter")
    void tryingToGuessAlreadyFailedLetter() {
        Game game = new Game(() -> "abcd", 5, new CallCountingUI("xxxxabcd"));
        CallCountingUI.resetCounters();

        game.run();

        assertCalls(4, 1, 0, 3, 1, 0);
    }
}
