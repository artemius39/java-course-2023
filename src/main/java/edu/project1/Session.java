package edu.project1;

public interface Session {
    char HIDDEN = '*';

    GuessResult tryGuess(char guess);

    String getWord();

    int mistakesAllowed();

    String getAnswer();
}
