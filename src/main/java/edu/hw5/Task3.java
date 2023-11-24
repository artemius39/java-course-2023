package edu.hw5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

public final class Task3 {
    private static final List<DateParser> PARSERS = List.of(
            DateParser.ofPattern("yyyy-MM-dd"),
            DateParser.ofPattern("yyyy-M-d"),
            DateParser.ofPattern("d/M/yyyy"),
            DateParser.ofPattern("d/M/yy"),
            DateParser.ofPattern("dd/MM/yyyy"),
            DateParser.ago("day", ChronoUnit.DAYS),
            DateParser.ago("month", ChronoUnit.MONTHS),
            DateParser.ago("year", ChronoUnit.YEARS),
            DateParser.ofWord("yesterday", () -> LocalDate.now().minusDays(1)),
            DateParser.ofWord("today", LocalDate::now),
            DateParser.ofWord("tomorrow", () -> LocalDate.now().plusDays(1))
    );

    public static Optional<LocalDate> parseDate(String dateString) {
        Optional<LocalDate> result = Optional.empty();

        for (DateParser parser : PARSERS) {
            result = parser.parseDate(dateString);
            if (result.isPresent()) {
                break;
            }
        }

        return result;
    }

    private Task3() {
    }

    @FunctionalInterface
    private interface DateParser {
        static DateParser ofPattern(String pattern) {
            return dateString -> {
                try {
                    return Optional.of(LocalDate.parse(dateString, DateTimeFormatter.ofPattern(pattern)));
                } catch (DateTimeParseException e) {
                    return Optional.empty();
                }
            };
        }

        static DateParser ofWord(String word, Supplier<LocalDate> dateSupplier) {
            return dateString -> dateString.equals(word) ? Optional.of(dateSupplier.get()) : Optional.empty();
        }

        static DateParser ago(String unitName, TemporalUnit unit) {
            return dateString -> {
                if (!dateString.matches("\\d+ " + unitName + "s ago|1 " + unitName + " ago")) {
                    return Optional.empty();
                }

                Scanner scanner = new Scanner(dateString);
                try {
                    int unitsAgo = scanner.nextInt();
                    return Optional.of(LocalDate.now().minus(unitsAgo, unit));
                } catch (InputMismatchException e) {
                    // Overflow
                    return Optional.empty();
                }
            };
        }

        Optional<LocalDate> parseDate(String dateString);
    }
}
