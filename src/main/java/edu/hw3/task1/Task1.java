package edu.hw3.task1;

import java.util.Objects;

public final class Task1 {
    public static String atbashCipher(String string) {
        Objects.requireNonNull(string);
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            int ch = chars[i];
            if ('a' <= ch && ch <= 'z') {
                ch = 'a' + 'z' - ch;
            } else if ('A' <= ch && ch <= 'Z') {
                ch = 'A' + 'Z' - ch;
            }
            chars[i] = (char) ch;
        }

        return new String(chars);
    }

    private Task1() {
    }
}
