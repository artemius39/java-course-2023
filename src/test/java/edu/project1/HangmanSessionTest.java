package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HangmanSessionTest {
    @Test
    @DisplayName("Session won't start on null word")
    void nullWord() {
        assertThrows(NullPointerException.class, () -> new HangmanSession(null, 10));
    }

    @Test
    @DisplayName("Session won't start on negative mistake count")
    void negativeMistakeCount() {
        assertThrows(IllegalArgumentException.class, () -> new HangmanSession("bibaboba", -42));
    }

    @Test
    @DisplayName("Correct guess")
    void correctGuess() {
        Session session = new HangmanSession("abc", 10);

        GuessResult result = session.tryGuess('a');
        String word = session.getWord();

        assertThat(result).isEqualTo(GuessResult.CORRECT);
        assertThat(word).isEqualTo("a**");
    }

    @Test
    @DisplayName("Incorrect guess")
    void incorrectGuess() {
        Session session = new HangmanSession("abc", 10);

        GuessResult result = session.tryGuess('x');
        String word = session.getWord();

        assertThat(result).isEqualTo(GuessResult.INCORRECT);
        assertThat(word).isEqualTo("***");
    }

    @Test
    @DisplayName("Initial state")
    void initialState() {
        Session session = new HangmanSession("abc", 10);

        String word = session.getWord();

        assertThat(word).isEqualTo("***");
    }

    @Test
    @DisplayName("One guess victory")
    void oneGuessVictory() {
        Session session = new HangmanSession("aaaa", 0);

        GuessResult result = session.tryGuess('a');
        String word = session.getWord();

        assertThat(result).isEqualTo(GuessResult.VICTORY);
        assertThat(word).isEqualTo("aaaa");
    }

    @Test
    @DisplayName("One guess loss")
    void oneGuessLoss() {
        Session session = new HangmanSession("bibaboba", 0);

        GuessResult result = session.tryGuess('x');
        String word = session.getWord();

        assertThat(result).isEqualTo(GuessResult.LOSS);
        assertThat(word).isEqualTo("bibaboba");
    }

    @Test
    @DisplayName("Multiple guesses loss")
    void multipleGuessesLoss() {
        Session session = new HangmanSession("ab", 1);

        GuessResult result1 = session.tryGuess('a');
        String word1 = session.getWord();
        GuessResult result2 = session.tryGuess('x');
        String word2 = session.getWord();
        GuessResult result3 = session.tryGuess('c');
        String word3 = session.getWord();

        assertThat(result1).isEqualTo(GuessResult.CORRECT);
        assertThat(word1).isEqualTo("a*");
        assertThat(result2).isEqualTo(GuessResult.INCORRECT);
        assertThat(word2).isEqualTo("a*");
        assertThat(result3).isEqualTo(GuessResult.LOSS);
        assertThat(word3).isEqualTo("ab");
    }

    @Test
    @DisplayName("Multiple guesses victory")
    void multipleGuessesVictory() {
        Session session = new HangmanSession("ab", 2);

        GuessResult result1 = session.tryGuess('a');
        String word1 = session.getWord();
        GuessResult result2 = session.tryGuess('x');
        String word2 = session.getWord();
        GuessResult result3 = session.tryGuess('b');
        String word3 = session.getWord();

        assertThat(result1).isEqualTo(GuessResult.CORRECT);
        assertThat(word1).isEqualTo("a*");
        assertThat(result2).isEqualTo(GuessResult.INCORRECT);
        assertThat(word2).isEqualTo("a*");
        assertThat(result3).isEqualTo(GuessResult.VICTORY);
        assertThat(word3).isEqualTo("ab");
    }

    @Test
    @DisplayName("Trying to guess already guessed letter")
    void alreadyGuessed() {
        Session session = new HangmanSession("abcd", 2);

        session.tryGuess('a');
        GuessResult result = session.tryGuess('a');

        assertThat(result).isEqualTo(GuessResult.INCORRECT);
    }

    @Test
    @DisplayName("Invalid character as a guess")
    void invalidCharacter() {
        Session session = new HangmanSession("abc", 2);

        GuessResult result = session.tryGuess('\n');

        assertThat(result).isEqualTo(GuessResult.INCORRECT);
    }

    @Test
    @DisplayName("Trying to guess after losing")
    void guessingAfterLosing() {
        Session session = new HangmanSession("abc", 0);

        session.tryGuess('x');

        assertThrows(IllegalStateException.class, () -> session.tryGuess('x'));
    }

    @Test
    @DisplayName("Trying to guess after winning")
    void guessingAfterWinning() {
        Session session = new HangmanSession("a", 0);

        session.tryGuess('a');

        assertThrows(IllegalStateException.class, () -> session.tryGuess('a'));
    }
}
