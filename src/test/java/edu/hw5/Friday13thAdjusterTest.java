package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.Temporal;
import static edu.hw5.Friday13thAdjuster.nextFriday13th;
import static edu.hw5.TestUtils.assertYearAndMonth;

class Friday13thAdjusterTest {
    @Test
    @DisplayName("This month")
    void thisMonth() {
        LocalDate date = LocalDate.of(2023, Month.OCTOBER, 1);

        Temporal nextFriday13th = date.with(nextFriday13th());

        assertYearAndMonth(nextFriday13th, 2023, 10);
    }

    @Test
    @DisplayName("This year")
    void thisYear() {
        LocalDate date = LocalDate.of(2023, Month.FEBRUARY, 1);

        Temporal nextFriday13th = date.with(nextFriday13th());

        assertYearAndMonth(nextFriday13th, 2023, 10);
    }

    @Test
    @DisplayName("Different year")
    void differentYear() {
        LocalDate date = LocalDate.of(2023, Month.NOVEMBER, 13);

        Temporal nextFriday13th = date.with(nextFriday13th());

        assertYearAndMonth(nextFriday13th, 2024, 9);
    }

    @Test
    @DisplayName("On Friday 13th")
    void onFriday13th() {
        LocalDate date = LocalDate.of(2023, Month.OCTOBER, 13);

        Temporal nextFriday13th = date.with(nextFriday13th());

        assertYearAndMonth(nextFriday13th, 2024, 9);
    }
}
