package edu.project1;

public interface UserInterface {
    char GIVE_UP_CHAR = '#';

    char askForAGuess(String word, int i);

    void victoryMessage(String answer);

    void lossMessage(String answer);

    void successfulGuessMessage();

    void unsuccessfulGuessMessage();
}
