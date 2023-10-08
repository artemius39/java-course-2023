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

        String minutesStr = minutesAndSeconds[0];
        String secondsStr = minutesAndSeconds[1];
        if (!minutesStr.matches("^\\d{2,}$") || !secondsStr.matches("^\\d{2}$")) {
            return ERROR;
        }

        long minutes = Long.parseLong(minutesStr);
        long seconds = Long.parseLong(secondsStr);
        if (seconds >= SECONDS_PER_MINUTE) {
            return ERROR;
        }

        return minutes * SECONDS_PER_MINUTE + seconds;
    }
}
