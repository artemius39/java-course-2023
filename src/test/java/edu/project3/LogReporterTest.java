package edu.project3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class LogReporterTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");

    @Test
    @DisplayName("No time limits")
    void noTimeLimits() {
        LogReporter reporter = new LogReporter();
        Stream<LogRecord> logs = Stream.of(
                new LogRecord(
                        "93.180.71.3", "-",
                        OffsetDateTime.parse("17/May/2015:08:05:32 +0300", FORMATTER),
                        LogRecord.Method.GET, "/downloads/product_1", 304, 0,
                        "https://referrer.com", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                ),
                new LogRecord(
                        "93.180.71.3", "john",
                        OffsetDateTime.parse("17/May/2015:08:05:23 -1000", FORMATTER),
                        LogRecord.Method.HEAD, "/downloads/product_2", 200, 123,
                        "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                ),
                new LogRecord(
                        "80.91.33.133", "-",
                        OffsetDateTime.parse("17/Nov/2015:08:05:24 +0000", FORMATTER),
                        LogRecord.Method.POST, "/downloads/product_3", 404, 0,
                        "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
                )
        );

        LogReport report = reporter.makeReport(logs, null, null, "nginx_logs");

        assertThat(report).isEqualTo(new LogReport(
                null, null, "nginx_logs", 3, 123 / 3,
                Map.of(
                        "/downloads/product_1", 1,
                        "/downloads/product_2", 1,
                        "/downloads/product_3", 1
                ),
                Map.of(
                        304, 1,
                        200, 1,
                        404, 1
                ),
                Map.of(
                        LogRecord.Method.GET, 1,
                        LogRecord.Method.HEAD, 1,
                        LogRecord.Method.POST, 1
                ),
                Map.of(
                        ZoneOffset.of("+0000"), 1,
                        ZoneOffset.of("+0300"), 1,
                        ZoneOffset.of("-1000"), 1
                )
        ));
    }

    @Test
    @DisplayName("With from constraint")
    void withFromConstraint() {
        LogReporter reporter = new LogReporter();
        Stream<LogRecord> logs = Stream.of(
                new LogRecord(
                        "93.180.71.3", "-",
                        OffsetDateTime.parse("17/May/2015:08:05:32 +0300", FORMATTER),
                        LogRecord.Method.GET, "/downloads/product_1", 304, 0,
                        "https://referrer.com", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                ),
                new LogRecord(
                        "93.180.71.3", "john",
                        OffsetDateTime.parse("18/May/2015:08:05:23 -1000", FORMATTER),
                        LogRecord.Method.HEAD, "/downloads/product_2", 200, 123,
                        "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                ),
                new LogRecord(
                        "80.91.33.133", "-",
                        OffsetDateTime.parse("17/Nov/2015:08:05:24 +0000", FORMATTER),
                        LogRecord.Method.POST, "/downloads/product_3", 404, 0,
                        "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
                )
        );
        OffsetDateTime from = OffsetDateTime.parse("18/May/2015:00:00:00 +0000", FORMATTER);

        LogReport report = reporter.makeReport(logs, from, null, "nginx_logs");

        assertThat(report).isEqualTo(new LogReport(
                from, null, "nginx_logs", 2, 123 / 2,
                Map.of(
                        "/downloads/product_2", 1,
                        "/downloads/product_3", 1
                ),
                Map.of(
                        200, 1,
                        404, 1
                ),
                Map.of(
                        LogRecord.Method.HEAD, 1,
                        LogRecord.Method.POST, 1
                ),
                Map.of(
                        ZoneOffset.of("+0000"), 1,
                        ZoneOffset.of("-1000"), 1
                )
        ));
    }

    @Test
    @DisplayName("With to constraint")
    void withToConstraint() {
        LogReporter reporter = new LogReporter();
        Stream<LogRecord> logs = Stream.of(
                new LogRecord(
                        "93.180.71.3", "-",
                        OffsetDateTime.parse("17/May/2015:08:05:32 +0300", FORMATTER),
                        LogRecord.Method.GET, "/downloads/product_1", 304, 0,
                        "https://referrer.com", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                ),
                new LogRecord(
                        "93.180.71.3", "john",
                        OffsetDateTime.parse("18/May/2015:08:05:23 -1000", FORMATTER),
                        LogRecord.Method.HEAD, "/downloads/product_2", 200, 123,
                        "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                ),
                new LogRecord(
                        "80.91.33.133", "-",
                        OffsetDateTime.parse("17/Nov/2015:08:05:24 +0000", FORMATTER),
                        LogRecord.Method.POST, "/downloads/product_3", 404, 0,
                        "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
                )
        );
        OffsetDateTime to = OffsetDateTime.parse("18/Oct/2015:00:00:00 +0000", FORMATTER);

        LogReport report = reporter.makeReport(logs, null, to, "nginx_logs");

        assertThat(report).isEqualTo(new LogReport(
                null, to, "nginx_logs", 2, 123 / 2,
                Map.of(
                        "/downloads/product_1", 1,
                        "/downloads/product_2", 1
                ),
                Map.of(
                        304, 1,
                        200, 1
                ),
                Map.of(
                        LogRecord.Method.GET, 1,
                        LogRecord.Method.HEAD, 1
                ),
                Map.of(
                        ZoneOffset.of("+0300"), 1,
                        ZoneOffset.of("-1000"), 1
                )
        ));
    }

    @Test
    @DisplayName("With both constraints")
    void withBothConstraints() {
        LogReporter reporter = new LogReporter();
        Stream<LogRecord> logs = Stream.of(
                new LogRecord(
                        "93.180.71.3", "-",
                        OffsetDateTime.parse("17/May/2015:08:05:32 +0300", FORMATTER),
                        LogRecord.Method.GET, "/downloads/product_1", 304, 0,
                        "https://referrer.com", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                ),
                new LogRecord(
                        "93.180.71.3", "john",
                        OffsetDateTime.parse("18/May/2015:08:05:23 -1000", FORMATTER),
                        LogRecord.Method.HEAD, "/downloads/product_2", 200, 123,
                        "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                ),
                new LogRecord(
                        "80.91.33.133", "-",
                        OffsetDateTime.parse("17/Nov/2015:08:05:24 +0000", FORMATTER),
                        LogRecord.Method.POST, "/downloads/product_3", 404, 0,
                        "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
                )
        );
        OffsetDateTime from = OffsetDateTime.parse("18/May/2015:00:00:00 +0000", FORMATTER);
        OffsetDateTime to = OffsetDateTime.parse("18/Oct/2015:00:00:00 +0000", FORMATTER);

        LogReport report = reporter.makeReport(logs, from, to, "nginx_logs");

        assertThat(report).isEqualTo(new LogReport(
                from, to, "nginx_logs", 1, 123,
                Map.of(
                        "/downloads/product_2", 1
                ),
                Map.of(
                        200, 1
                ),
                Map.of(
                        LogRecord.Method.HEAD, 1
                ),
                Map.of(
                        ZoneOffset.of("-1000"), 1
                )
        ));
    }

    @Test
    @DisplayName("No log matches constraints")
    void noLogMatchesConstraints() {
        LogReporter reporter = new LogReporter();
        Stream<LogRecord> logs = Stream.of(
                new LogRecord(
                        "93.180.71.3", "-",
                        OffsetDateTime.parse("17/May/2015:08:05:32 +0300", FORMATTER),
                        LogRecord.Method.GET, "/downloads/product_1", 304, 0,
                        "https://referrer.com", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                ),
                new LogRecord(
                        "93.180.71.3", "john",
                        OffsetDateTime.parse("18/May/2015:08:05:23 -1000", FORMATTER),
                        LogRecord.Method.HEAD, "/downloads/product_2", 200, 123,
                        "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
                ),
                new LogRecord(
                        "80.91.33.133", "-",
                        OffsetDateTime.parse("17/Nov/2015:08:05:24 +0000", FORMATTER),
                        LogRecord.Method.POST, "/downloads/product_3", 404, 0,
                        "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
                )
        );
        OffsetDateTime from = OffsetDateTime.parse("18/Oct/2015:00:00:00 +0000", FORMATTER);
        OffsetDateTime to = OffsetDateTime.parse("18/May/2015:00:00:00 +0000", FORMATTER);

        LogReport report = reporter.makeReport(logs, from, to, "nginx_logs");

        assertThat(report).isEqualTo(new LogReport(
                from, to, "nginx_logs", 0, 0,
                Map.of(),
                Map.of(),
                Map.of(),
                Map.of()
        ));
    }
}
