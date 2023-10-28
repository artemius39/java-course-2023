package edu.project1;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HangmanSession implements Session {
    private final String word;
    private final Set<Character> guessedLetters;
    private final Set<Character> failedGuesses;
    private int mistakesAllowed;

    public HangmanSession(String word, int mistakesAllowed) {
        Objects.requireNonNull(word);
        if (mistakesAllowed < 0) {
            throw new IllegalArgumentException("Mistake count cannot be negative: " + mistakesAllowed);
        }

        this.word = word;
        this.mistakesAllowed = mistakesAllowed;
        this.guessedLetters = new HashSet<>();
        this.failedGuesses = new HashSet<>();
    }

    @Override
    public GuessResult tryGuess(char guess) {
        if (lost()) {
            throw new IllegalStateException("Trying to guess after losing");
        }
        if (guessedLetters.contains(guess)) {
            return GuessResult.ALREADY_GUESSED;
        }
        if (failedGuesses.contains(guess)) {
            return GuessResult.ALREADY_FAILED;
        }

        boolean successfulGuess = false;
        boolean allGuessed = true;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (ch == guess) {
                successfulGuess = true;
                guessedLetters.add(ch);
            }
            allGuessed = allGuessed && guessedLetters.contains(ch);
        }

        if (!successfulGuess && allGuessed) {
            throw new IllegalStateException("Trying to guess after winning");
        }
        if (successfulGuess) {
            return allGuessed ? GuessResult.VICTORY : GuessResult.CORRECT;
        }

        failedGuesses.add(guess);
        return mistakesAllowed-- == 0 ? GuessResult.LOSS : GuessResult.INCORRECT;
    }

    private boolean lost() {
        return mistakesAllowed < 0;
    }

    @Override
    public String getWord() {
        char[] chars = word.toCharArray();

        for (int i = 0; i < word.length(); i++) {
            if (!guessedLetters.contains(word.charAt(i))) {
                chars[i] = HIDDEN;
            }
        }

        return new String(chars);
    }

    @Override
    public String getAnswer() {
        return word;
    }

    @Override
    public int mistakesAllowed() {
        return mistakesAllowed;
    }
}
