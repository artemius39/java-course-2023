package edu.hw8.task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BruteForcePasswordGenerator implements PasswordGenerator {
    private final List<Character> alphabet;
    private final Map<Character, Integer> indices;
    private final StringBuilder password;
    private final long maxPasswordNo;
    private long currentPasswordNo;

    BruteForcePasswordGenerator(List<Character> alphabet,  long maxPasswordNo) {
        this(alphabet, 0, maxPasswordNo);
    }

    BruteForcePasswordGenerator(List<Character> alphabet, long startPasswordNo, long maxPasswordNo) {
        currentPasswordNo = startPasswordNo;
        this.maxPasswordNo = maxPasswordNo;
        this.alphabet = alphabet;
        indices = new HashMap<>(alphabet.size());
        for (int index = 0; index < alphabet.size(); index++) {
            indices.put(alphabet.get(index), index);
        }
        password = nthPassword(currentPasswordNo);
    }

    private StringBuilder nthPassword(long n) {
        if (n == 0) {
            return new StringBuilder();
        }

        StringBuilder sb = new StringBuilder();
        long passwordLength = (long) Math.ceil(Math.log(n) / Math.log(alphabet.size()));

        long passwordNumber = n;
        while (passwordNumber > 0) {
            sb.append(alphabet.get((int) (passwordNumber % alphabet.size())));
            passwordNumber /= alphabet.size();
        }
        while (sb.length() < passwordLength) {
            sb.append(alphabet.getFirst());
        }

        return sb;
    }

    @Override
    public String next() {
        String result = password.toString();

        int lastIncrementableCharIndex = getLastIncrementableCharIndex();

        if (lastIncrementableCharIndex == -1) {
            password.append(alphabet.getFirst());
        } else {
            char lastIncrementableChar = password.charAt(lastIncrementableCharIndex);
            int lastIncrementableCharListIndex = indices.get(lastIncrementableChar);
            char incremented = alphabet.get(lastIncrementableCharListIndex + 1);
            password.setCharAt(lastIncrementableCharIndex, incremented);
        }

        for (int index = lastIncrementableCharIndex + 1; index < password.length(); index++) {
            password.setCharAt(index, alphabet.getFirst());
        }

        currentPasswordNo++;
        return result;
    }

    @Override
    public boolean hasNext() {
        return currentPasswordNo < maxPasswordNo;
    }

    private int getLastIncrementableCharIndex() {
        int index = password.length() - 1;
        while (index >= 0 && password.charAt(index) == alphabet.getLast()) {
            index--;
        }
        return index;
    }
}
