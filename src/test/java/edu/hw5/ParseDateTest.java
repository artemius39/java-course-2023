package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static edu.hw5.Task3.parseDate;
import static org.assertj.core.api.Assertions.assertThat;

class ParseDateTest {
    private void assertParse(String dateString, int expectedYear, int expectedMonth, int expectedDayOfMonth) {
        assertParse(dateString, LocalDate.of(expectedYear, expectedMonth, expectedDayOfMonth));
    }

    private void assertParse(String dateString, LocalDate expected) {
        assertThat(parseDate(dateString))
                .isPresent()
                .contains(expected);
    }

    private void assertNoParse(String dateString) {
        assertThat(parseDate(dateString)).isEmpty();
    }

    @Test
    @DisplayName("yyyy-MM-dd")
    void yyyyMMdd() {
        assertParse("2020-10-10", 2020, 10, 10);
    }

    @Test
    @DisplayName("yyyy-M-d")
    void yyyyMd() {
        assertParse("2020-12-2", 2020, 12, 2);
    }
    
    @Test
    @DisplayName("d/M/yyyy")
    void dMyyyy() {
        assertParse("1/3/1976", 1976, 3, 1);
    }

    @Test
    @DisplayName("d/M/yy")
    void dMyy() {
        assertParse("1/3/20", 2020, 3, 1);
    }

    @Test
    @DisplayName("tomorrow")
    void tomorrow() {
        assertParse("tomorrow", LocalDate.now().plusDays(1));
    }
    
    @Test
    @DisplayName("today")
    void today() {
        assertParse("today", LocalDate.now());
    }

    @Test
    @DisplayName("yesterday")
    void yesterday() {
        assertParse("yesterday", LocalDate.now().minusDays(1));
    }
    
    @Test
    @DisplayName("1 day ago")
    void oneDayAgo() {
        assertParse("1 day ago", LocalDate.now().minusDays(1));
    }

    @Test
    @DisplayName("Many days ago")
    void manyDaysAgo() {
        assertParse("2234 days ago", LocalDate.now().minusDays(2234));
    }

    @Test
    @DisplayName("1 month ago")
    void oneMonthAgo() {
        assertParse("1 month ago", LocalDate.now().minusMonths(1));
    }

    @Test
    @DisplayName("Many months ago")
    void manyMonthsAgo() {
        assertParse("42 months ago", LocalDate.now().minusMonths(42));
    }

    @Test
    @DisplayName("One year ago")
    void oneYearAgo() {
        assertParse("1 year ago", LocalDate.now().minusYears(1));
    }

    @Test
    @DisplayName("Many years ago")
    void manyYearsAgo() {
        assertParse("30 years ago", LocalDate.now().minusYears(30));
    }

    @Test
    @DisplayName("Invalid format")
    void invalidFormat() {
        assertNoParse("bibaboba");
    }

    @Test
    @DisplayName("Overflow")
    void overflow() {
        assertNoParse("10000000000000000 days ago");
    }
}
