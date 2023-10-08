package edu.hw1;

public final class Task5 {
    private Task5() {
    }

    private static final int RADIX = 10;

    public static boolean isPalindromeDescendant(int number) {
        if (number < 0) {
            return false;
        } else if (number < RADIX) {
            // check for one-digit numbers
            return true;
        } else {
            return isPalindromeDescendantImpl(String.valueOf(number));
        }
    }

    private static boolean isPalindromeDescendantImpl(String number) {
        if (number.length() == 1) {
            return false;
        }
        if (isPalindrome(number)) {
            return true;
        }
        if (number.length() % 2 == 1) {
            return false;
        }

        StringBuilder sb = new StringBuilder(number.length() / 2);
        for (int i = 0; i < number.length(); i += 2) {
            int digit1 = Character.getNumericValue(number.charAt(i));
            int digit2 = Character.getNumericValue(number.charAt(i + 1));
            sb.append(digit1 + digit2);
        }

        return isPalindromeDescendantImpl(sb.toString());
    }

    private static boolean isPalindrome(String number) {
        for (int i = 0, j = number.length() - 1; i < j; i++, j--) {
            if (number.charAt(i) != number.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}
