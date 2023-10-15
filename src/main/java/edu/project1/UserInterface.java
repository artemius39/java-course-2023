package edu.project1;

public interface UserInterface {
    char GIVE_UP_CHAR = '#';

    char askForAGuess(String word, int i);

    void victoryMessage();

    void lossMessage();

    void successfulGuessMessage();

    void unsuccessfulGuessMessage();

    void revealAnswer(String answer);
}
