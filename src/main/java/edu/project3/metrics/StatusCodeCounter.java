package edu.project3.metrics;

import edu.project3.LogRecord;
import java.util.HashMap;
import java.util.Map;

public class StatusCodeCounter implements Metric<Map<Integer, Integer>> {
    private final Map<Integer, Integer> statusCodeCounter;

    public StatusCodeCounter() {
        statusCodeCounter = new HashMap<>();
    }

    @Override
    public void count(LogRecord log) {
        statusCodeCounter.merge(log.statusCode(), 1, Integer::sum);
    }

    @Override
    public Map<Integer, Integer> get() {
        return statusCodeCounter;
    }
}
