package edu.hw3.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Task2 {

    public static List<String> clusterize(String parentheses) {
        Objects.requireNonNull(parentheses);
        int balance = 0;
        List<String> clusters = new ArrayList<>();
        StringBuilder currentCluster = new StringBuilder(parentheses.length());

        for (int i = 0; i < parentheses.length(); i++) {
            char currentCharacter = parentheses.charAt(i);
            currentCluster.append(currentCharacter);
            if (currentCharacter == '(') {
                balance++;
            } else if (currentCharacter == ')') {
                balance--;
            } else {
                throw new IllegalArgumentException(
                        "Attempting to clusterize a string with a non-parenthesis character"
                );
            }

            if (balance < 0) {
                throw unbalancedStringException();
            }
            if (balance == 0) {
                clusters.add(currentCluster.toString());
                currentCluster.delete(0, currentCluster.length());
            }
        }
        if (balance > 0) {
            throw unbalancedStringException();
        }

        return clusters;
    }

    private static IllegalArgumentException unbalancedStringException() {
        return new IllegalArgumentException("Attempting to clusterize an unbalanced string");
    }

    private Task2() {
    }
}
