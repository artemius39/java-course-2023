package edu.project3.metrics;

import edu.project3.LogRecord;
import java.util.EnumMap;
import java.util.Map;

public class MethodCounter implements Metric<Map<LogRecord.Method, Integer>> {
    private final Map<LogRecord.Method, Integer> methodFrequencies;

    public MethodCounter() {
        methodFrequencies = new EnumMap<>(LogRecord.Method.class);
    }

    @Override
    public void count(LogRecord log) {
        methodFrequencies.merge(log.method(), 1, Integer::sum);
    }

    @Override
    public Map<LogRecord.Method, Integer> get() {
        return methodFrequencies;
    }
}
