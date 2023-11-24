package edu.hw5;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Task6 {

    public static boolean isSubsequence(String stringToSearchIn, String stringToSearchFor) {
        String regex = ".*"
                       + stringToSearchFor.chars()
                               .mapToObj(ch -> Pattern.quote(String.valueOf((char) ch)))
                               .collect(Collectors.joining(".*"))
                       + ".*";
        return stringToSearchIn.matches(regex);
    }

    private Task6() {
    }
}
