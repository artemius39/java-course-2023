package edu.hw5;

import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Friday13thAdjuster implements TemporalAdjuster {

    private static final int THE_DAY_OF_FRIDAY_13TH = 13;
    private static final int FRIDAY = 5;

    private static Friday13thAdjuster instance = null;

    public static Friday13thAdjuster nextFriday13th() {
        if (instance == null) {
            instance = new Friday13thAdjuster();
        }
        return instance;
    }

    public static List<Temporal> allFriday13thsOfYear(int year) {
        TemporalAdjuster adjuster = nextFriday13th();
        return Stream.iterate(Year.of(year)
                                .atDay(1)
                                .with(adjuster),
                        friday13th -> friday13th.getYear() == year,
                        friday13th -> friday13th.with(adjuster))
                .collect(Collectors.toList());
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        Temporal monthOfNext13th = temporal.get(ChronoField.DAY_OF_MONTH) < THE_DAY_OF_FRIDAY_13TH ? temporal
                : temporal.plus(1, ChronoUnit.MONTHS);
        Temporal next13th = monthOfNext13th.with(ChronoField.DAY_OF_MONTH, THE_DAY_OF_FRIDAY_13TH);
        while (next13th.get(ChronoField.DAY_OF_WEEK) != FRIDAY) {
            next13th = next13th.plus(1, ChronoUnit.MONTHS);
        }
        return next13th;
    }
}
