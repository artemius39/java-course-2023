package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.Duration;
import static edu.hw5.Task1.averageSessionDuration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task1Test {
    @Test
    @DisplayName("Empty string")
    void emptyString() {
        assertThrows(IllegalArgumentException.class, () -> averageSessionDuration(""));
    }

    @Test
    @DisplayName("Null string")
    void nullString() {
        assertThrows(NullPointerException.class, () -> averageSessionDuration(null));
    }

    @ParameterizedTest
    @DisplayName("Invalid format")
    @CsvSource({
            "2023-11-11, 20:42",
            "2023-11-11 21:14",
            "11-11-2023, 20:42 - 11-11-2023, 21:42",
            "2023-18-10, 07:34 - 2023-30-10, 09:45",
            "2023.11.11, 04:20 - 2023.12.10, 10:30",
            "2023-11-11 - 2023-12-12",
            "2023-11-11, 20:55 - 2023-11-11, 23:43 - 2023-11-11, 23:44",
            "2023-11-11, 21:30 - 2023-11-11, 21:60",
            "2023-11-11, 21:30 - 2023-11-11, 24:00",
            "2023-11-5, 21:39 - 2023-11-11, 23:30"
    })
    void invalidFormat(String log) {
        assertThrows(IllegalArgumentException.class, () -> averageSessionDuration(log));
    }

    @Test
    @DisplayName("Negative duration")
    void negativeDuration() {
        assertThrows(IllegalArgumentException.class, () -> averageSessionDuration("2023-11-11, 20:58 - 2023-11-11, 19:30"));
    }

    @Test
    @DisplayName("Zero duration")
    void zeroDuration() {
        Duration actual = averageSessionDuration("2023-11-11, 20:59 - 2023-11-11, 20:59");

        assertThat(actual).isZero();
    }

    @Test
    @DisplayName("One log")
    void oneLog() {
        Duration actual = averageSessionDuration("2023-11-11, 21:15 - 2023-11-11, 21:16");

        assertThat(actual).isEqualTo(Duration.ofMinutes(1));
    }

    @Test
    @DisplayName("Multiple logs")
    void multipleLogs() {
        Duration actual = averageSessionDuration("""
                2022-03-12, 20:20 - 2022-03-12, 23:50
                2022-04-01, 21:30 - 2022-04-02, 01:20""");

        assertThat(actual).isEqualTo(Duration.ofHours(3).plusMinutes(40));
    }

    @Test
    @DisplayName("Next day")
    void nextDay() {
        Duration actual = averageSessionDuration("2023-11-11, 23:59 - 2023-11-12, 00:00");

        assertThat(actual).isEqualTo(Duration.ofMinutes(1));
    }
    
    @Test
    @DisplayName("Seconds")
    void seconds() {
        Duration actual = averageSessionDuration("""
                2023-11-11, 21:56 - 2023-11-11, 21:58
                2023-11-11, 21:56 - 2023-11-11, 21:59
                2023-11-11, 21:56 - 2023-11-11, 21:59""");

        assertThat(actual).isEqualTo(Duration.ofMinutes(2).plusSeconds(40));
    }

    @Test
    @DisplayName("Non-integer average")
    void nonIntegerAverage() {
        Duration actual = averageSessionDuration("""
                2023-11-11, 22:01 - 2023-11-11, 22:02
                2023-11-11, 22:01 - 2023-11-11, 22:01
                2023-11-11, 22:01 - 2023-11-11, 22:01
                2023-11-11, 22:01 - 2023-11-11, 22:01
                2023-11-11, 22:01 - 2023-11-11, 22:01
                2023-11-11, 22:01 - 2023-11-11, 22:01
                2023-11-11, 22:01 - 2023-11-11, 22:01""");

        assertThat(actual).isEqualTo(Duration.ofMillis(60000 / 7));
    }
}
