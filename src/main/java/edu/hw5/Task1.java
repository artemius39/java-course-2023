package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Task1 {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
    private static final String INVALID_LOG_FORMAT = "invalid log format";

    public static Duration averageSessionDuration(String logs) {
        Objects.requireNonNull(logs, "logs cannot be null");
        if (logs.isEmpty()) {
            throw new IllegalArgumentException("logs must contain at least one log");
        }

        return Duration.ofMillis(logs.lines()
                .map(Task1::parseLog)
                .collect(Collectors.averagingLong(Duration::toMillis))
                .longValue()
        );
    }

    private static Duration parseLog(String sessionString) {
        String[] split = sessionString.split(" - ");
        if (split.length != 2) {
            throw new IllegalArgumentException(INVALID_LOG_FORMAT);
        }

        Duration result;
        try {
            result = Duration.between(
                    LocalDateTime.parse(split[0], FORMATTER),
                    LocalDateTime.parse(split[1], FORMATTER)
            );
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_LOG_FORMAT, e);
        }

        if (result.isNegative()) {
            throw new IllegalArgumentException("negative session duration");
        }
        return result;
    }

    private Task1() {
    }
}
