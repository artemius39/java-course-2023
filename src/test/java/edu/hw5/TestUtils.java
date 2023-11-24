package edu.hw5;

import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import static org.assertj.core.api.Assertions.assertThat;

public final class TestUtils {
    public static void assertYearAndMonth(Temporal actual, int year, int month) {
        assertThat(actual.get(ChronoField.YEAR)).isEqualTo(year);
        assertThat(actual.get(ChronoField.MONTH_OF_YEAR)).isEqualTo(month);
        assertThat(actual.get(ChronoField.DAY_OF_MONTH)).isEqualTo(13);
    }

    private TestUtils() {
    }
}
