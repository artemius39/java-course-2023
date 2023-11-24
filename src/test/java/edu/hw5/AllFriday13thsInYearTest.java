package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.temporal.Temporal;
import java.util.List;
import static edu.hw5.Friday13thAdjuster.allFriday13thsOfYear;
import static edu.hw5.TestUtils.assertYearAndMonth;
import static org.assertj.core.api.Assertions.assertThat;

class AllFriday13thsInYearTest {
    @Test
    @DisplayName("One Friday 13th")
    void oneFriday13th() {
        List<Temporal> friday13ths = allFriday13thsOfYear(2025);
        
        assertThat(friday13ths).size().isOne();
        assertYearAndMonth(friday13ths.get(0), 2025, 6);
    }

    @Test
    @DisplayName("Multiple Friday 13ths")
    void multipleFriday13ths() {
        List<Temporal> friday13ths = allFriday13thsOfYear(2026);

        assertThat(friday13ths).size().isEqualTo(3);
        assertYearAndMonth(friday13ths.get(0), 2026, 2);
        assertYearAndMonth(friday13ths.get(1), 2026, 3);
        assertYearAndMonth(friday13ths.get(2), 2026, 11);
    }
}
