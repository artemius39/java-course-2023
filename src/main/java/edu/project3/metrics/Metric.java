package edu.project3.metrics;

import edu.project3.LogRecord;

public interface Metric<T> {
    void count(LogRecord log);

    T get();
}
