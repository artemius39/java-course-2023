package edu.project3.metrics;

import edu.project3.LogRecord;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class TimeOffsetCounter implements Metric<Map<ZoneOffset, Integer>> {
    private final Map<ZoneOffset, Integer> offsetFrequencies;

    public TimeOffsetCounter() {
        offsetFrequencies = new HashMap<>();
    }

    @Override
    public void count(LogRecord log) {
        offsetFrequencies.merge(log.localTime().getOffset(), 1, Integer::sum);
    }

    @Override
    public Map<ZoneOffset, Integer> get() {
        return offsetFrequencies;
    }
}
