package edu.project1;

import java.util.Scanner;

@SuppressWarnings("RegexpSingleLineJava")
public class ConsoleInterface implements UserInterface {
    private static final String GIVE_UP_COMMAND = "give up";
    private final Scanner in;

    public ConsoleInterface() {
        this(new Scanner(System.in));
    }

    ConsoleInterface(Scanner in) {
        this.in = in;
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
                    return guess;
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

    @Override
    public void alreadyGuessedMessage() {
        System.out.println("You already guessed this letter!");
    }

    @Override
    public void alreadyFailedMessage() {
        System.out.println("You already tried to guess this letter, the guess was incorrect");
    }
}
