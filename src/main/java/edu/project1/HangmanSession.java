package edu.project1;

import java.util.Objects;

public class HangmanSession implements Session {
    private final String word;
    private final boolean[] wasGuessed;
    private int mistakesAllowed;

    public HangmanSession(String word, int mistakesAllowed) {
        Objects.requireNonNull(word);
        if (mistakesAllowed < 0) {
            throw new IllegalArgumentException("Mistake count cannot be negative: " + mistakesAllowed);
        }

        this.word = word;
        this.mistakesAllowed = mistakesAllowed;
        wasGuessed = new boolean[word.length()];
    }

    @Override
    public GuessResult tryGuess(char guess) {
        if (lost()) {
            throw new IllegalStateException("Trying to guess after losing");
        }

        boolean successfulGuess = false;
        boolean allGuessed = true;

        for (int i = 0; i < word.length(); i++) {
            if (!wasGuessed[i] && word.charAt(i) == guess) {
                successfulGuess = true;
                wasGuessed[i] = true;
            }
            allGuessed = allGuessed && wasGuessed[i];
        }

        if (!successfulGuess && allGuessed) {
            throw new IllegalStateException("Trying to guess after winning");
        }
        if (successfulGuess) {
            return allGuessed ? GuessResult.VICTORY : GuessResult.CORRECT;
        }
        if (mistakesAllowed-- == 0) {
            return GuessResult.LOSS;
        }
        return GuessResult.INCORRECT;
    }

    private boolean lost() {
        return mistakesAllowed < 0;
    }

    @Override
    public String getWord() {
        if (lost()) {
            return word;
        }
        char[] chars = word.toCharArray();

        for (int i = 0; i < word.length(); i++) {
            if (!wasGuessed[i]) {
                chars[i] = HIDDEN;
            }
        }

        return new String(chars);
    }

    @Override
    public int mistakesAllowed() {
        return mistakesAllowed;
    }
}
