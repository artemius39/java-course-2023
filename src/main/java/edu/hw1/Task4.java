package edu.hw1;

import java.util.Objects;

public final class Task4 {
    private Task4() {
    }

    public static String fixString(String broken) {
        Objects.requireNonNull(broken);

        char[] chars = broken.toCharArray();
        for (int i = 0; i + 1 < broken.length(); i += 2) {
            char tmp = chars[i];
            chars[i] = chars[i + 1];
            chars[i + 1] = tmp;
        }

        return new String(chars);
    }
}
