package edu.project1;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@SuppressWarnings("RegexpSingleLineJava")
public class ConsoleInterface implements UserInterface {
    private static final String GIVE_UP_COMMAND = "give up";
    private final Scanner in;
    private final Set<Character> triedLetters;

    public ConsoleInterface() {
        this(new Scanner(System.in));
    }

    ConsoleInterface(Scanner in) {
        this.in = in;
        triedLetters = new HashSet<>();
    }

    @Override
    public char askForAGuess(String word, int mistakesAllowed) {
        while (true) {
            System.out.println("The word: " + word);
            System.out.println("Mistakes allowed: " + mistakesAllowed);
            System.out.println("Enter a lowercase letter or give up by entering 'give up'");

            if (!in.hasNextLine()) {
                return GIVE_UP_CHAR;
            }
            String input = in.nextLine();

            if (input.equals(GIVE_UP_COMMAND)) {
                return GIVE_UP_CHAR;
            }
            if (input.length() == 1) {
                char guess = input.charAt(0);
                if (Character.isLowerCase(guess)) {
                    if (triedLetters.add(guess)) {
                        return guess;
                    } else {
                        System.out.println("You already tried this letter.");
                        continue;
                    }
                }
            }

            System.out.println(
                    "Incorrect input format. "
                            + "You must enter one lowercase letter or give up by entering 'give up'. "
                            + "Please try again"
            );
        }
    }

    @Override
    public void victoryMessage() {
        System.out.println("You win!!!");
    }

    @Override
    public void lossMessage() {
        System.out.println("You lose :(");
    }

    @Override
    public void successfulGuessMessage() {
        System.out.println("You have guessed correctly!");
    }

    @Override
    public void unsuccessfulGuessMessage() {
        System.out.println("Your guess is incorrect :(");
    }

    @Override
    public void revealAnswer(String answer) {
        System.out.println("The answer was '" + answer + "'");
    }
}
