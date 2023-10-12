package edu.hw1;

import java.util.Objects;

public final class Task1 {
    private static final int ERROR = -1;

    private static final int SECONDS_PER_MINUTE = 60;

    private Task1() {
    }

    public static long minutesToSeconds(String time) {
        Objects.requireNonNull(time);

        String[] minutesAndSeconds = time.split(":");
        if (minutesAndSeconds.length != 2) {
            return ERROR;
        }

        long minutes = getMinutes(minutesAndSeconds[0]);
        if (minutes == ERROR) {
            return ERROR;
        }
        long seconds = getSeconds(minutesAndSeconds[1]);
        if (seconds == ERROR || seconds >= SECONDS_PER_MINUTE) {
            return ERROR;
        }

        long result;
        try {
            result = StrictMath.addExact(StrictMath.multiplyExact(minutes, SECONDS_PER_MINUTE), seconds);
        } catch (ArithmeticException ignored) {
            result = ERROR;
        }

        return result;
    }

    private static long getMinutes(String minutesStr) {
        if (!minutesStr.matches("^\\d{2,}$")) {
            return ERROR;
        }
        try {
            return Long.parseLong(minutesStr);
        } catch (NumberFormatException ignored) {
            return ERROR;
        }
    }

    private static long getSeconds(String secondsStr) {
        if (!secondsStr.matches("^\\d{2}$")) {
            return ERROR;
        }
        try {
            return Long.parseLong(secondsStr);
        } catch (NumberFormatException ignored) {
            return ERROR;
        }
    }
}
