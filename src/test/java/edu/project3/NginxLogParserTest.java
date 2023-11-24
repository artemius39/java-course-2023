package edu.project3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NginxLogParserTest {
    private static Stream<Arguments> validLogs() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");
        return Stream.of(
                Arguments.of(
                        "93.180.71.3 - - [17/May/2015:08:05:32 +0300] \"GET /downloads/product_1 HTTP/1.1\" 304 0"
                        + " \"https://referrer.com\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
                        new LogRecord(
                                "93.180.71.3", "-",
                                OffsetDateTime.parse("17/May/2015:08:05:32 +0300", formatter),
                                LogRecord.Method.GET, "/downloads/product_1", 304, 0,
                                "https://referrer.com", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                        )
                ),
                Arguments.of(
                        "93.180.71.3 - john [17/May/2015:08:05:23 -1000] \"HEAD /downloads/product_2 HTTP/1.1\" 200 123"
                        + " \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
                        new LogRecord(
                                "93.180.71.3", "john",
                                OffsetDateTime.parse("17/May/2015:08:05:23 -1000", formatter),
                                LogRecord.Method.HEAD, "/downloads/product_2", 200, 123,
                                "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                        )
                ),
                Arguments.of(
                        "80.91.33.133 - - [17/Nov/2015:08:05:24 +0000] \"POST /downloads/product_3 HTTP/2\" 404 0"
                        + " \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"",
                        new LogRecord(
                                "80.91.33.133", "-",
                                OffsetDateTime.parse("17/Nov/2015:08:05:24 +0000", formatter),
                                LogRecord.Method.POST, "/downloads/product_3", 404, 0,
                                "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Valid logs")
    void validLogs(String log, LogRecord expected) {
        LogParser parser = new NginxLogParser();

        LogRecord actual = parser.parseLog(log);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Invalid date")
    void invalidDate() {
        LogParser parser = new NginxLogParser();
        String log = "93.180.71.3 - - [173/May/2015:08:05:32 +0300] \"GET /downloads/product_1 HTTP/1.1\" 304 0 "
                     + "\"https://referrer.com\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        assertThrows(InvalidLogFormatException.class, () -> parser.parseLog(log));
    }

    @Test
    @DisplayName("Invalid method")
    void invalidMethod() {
        LogParser parser = new NginxLogParser();
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0300] \"INVALID /downloads/product_1 HTTP/1.1\" 304 0 "
                     + "\"https://referrer.com\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        assertThrows(InvalidLogFormatException.class, () -> parser.parseLog(log));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10000000000000", "-1"})
    @DisplayName("Invalid status code")
    void invalidStatusCode(String statusCode) {
        LogParser parser = new NginxLogParser();
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0300] \"GET /downloads/product_1 HTTP/1.1\" " + statusCode
                     + " 0 \"https://referrer.com\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        assertThrows(InvalidLogFormatException.class, () -> parser.parseLog(log));
    }

    @Test
    @DisplayName("Invalid resource")
    void invalidResource() {
        LogParser parser = new NginxLogParser();
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0300] \"GET /downloads/prod%ct_1 HTTP/1.1\" 200"
                     + " 0 \"https://referrer.com\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        assertThrows(InvalidLogFormatException.class, () -> parser.parseLog(log));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10000000000000000000000000", "-1"})
    @DisplayName("Invalid response size")
    void invalidResponseSize(String responseSize) {
        LogParser parser = new NginxLogParser();
        String log = "93.180.71.3 - - [17/May/2015:08:05:32 +0300] \"GET /downloads/product_1 HTTP/1.1\" 200 "
                     + responseSize + " \"https://referrer.com\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        assertThrows(InvalidLogFormatException.class, () -> parser.parseLog(log));
    }


}
