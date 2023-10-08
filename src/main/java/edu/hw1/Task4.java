package edu.hw1;

import java.util.Objects;

public final class Task4 {
    private Task4() {
    }

    public static String fixString(String broken) {
        Objects.requireNonNull(broken);

        StringBuilder sb = new StringBuilder(broken.length());
        for (int i = 0; i + 1 < broken.length(); i += 2) {
            sb.append(broken.charAt(i + 1)).append(broken.charAt(i));
        }
        if (broken.length() % 2 == 1) {
            sb.append(broken.charAt(broken.length() - 1));
        }

        return sb.toString();
    }
}
