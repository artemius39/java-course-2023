package edu.project3.metrics;

import edu.project3.LogRecord;
import java.util.HashMap;
import java.util.Map;

public class ResourcesCounter implements Metric<Map<String, Integer>> {
    private final Map<String, Integer> sourcesRequested;

    public ResourcesCounter() {
        sourcesRequested = new HashMap<>();
    }

    @Override
    public void count(LogRecord log) {
        sourcesRequested.merge(log.resourceURI(), 1, Integer::sum);
    }

    @Override
    public Map<String, Integer> get() {
        return sourcesRequested;
    }
}
