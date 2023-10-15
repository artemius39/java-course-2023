package edu.project1;

import java.util.Objects;

public class Game {
    private static final Dictionary DEFAULT_DICTIONARY = EnglishDictionary::randomWord;
    private static final int DEFAULT_MISTAKE_COUNT = 10;
    private final Session session;
    private final UserInterface userInterface;

    public Game() {
        this(DEFAULT_DICTIONARY, DEFAULT_MISTAKE_COUNT);
    }

    public Game(Dictionary dictionary, int mistakesAllowed) {
        this(dictionary, mistakesAllowed, new ConsoleInterface());
    }

    Game(Dictionary dictionary, int mistakesAllowed, UserInterface userInterface) {
        Objects.requireNonNull(dictionary);
        Objects.requireNonNull(userInterface);

        this.userInterface = userInterface;

        String word = dictionary.randomWord();
        if (invalidWord(word)) {
            throw new IllegalArgumentException("Dictionary supplied illegal word: '" + word + "'");
        }

        session = new HangmanSession(word, mistakesAllowed);
    }



    private boolean invalidWord(String word) {
        if (word.isEmpty()) {
            return true;
        }
        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLowerCase(word.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public void run() {
        while (true) {
            char guess = userInterface.askForAGuess(session.getWord(), session.mistakesAllowed());
            if (guess == UserInterface.GIVE_UP_CHAR) {
                lose();
                break;
            }

            GuessResult guessResult = session.tryGuess(guess);

            if (isCorrect(guessResult)) {
                userInterface.successfulGuessMessage();
            } else {
                userInterface.unsuccessfulGuessMessage();
            }

            if (guessResult == GuessResult.VICTORY) {
                win();
                break;
            }
            if (guessResult == GuessResult.LOSS) {
                lose();
                break;
            }
        }
    }

    private void win() {
        userInterface.victoryMessage(session.getWord());
    }

    private void lose() {
        userInterface.lossMessage(session.getWord());
    }

    private boolean isCorrect(GuessResult guessResult) {
        return guessResult == GuessResult.VICTORY || guessResult == GuessResult.CORRECT;
    }
}
