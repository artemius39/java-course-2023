package edu.project3.metrics;

import edu.project3.LogRecord;

public class Counter implements Metric<Long> {
    private long counter;

    public Counter() {
        counter = 0;
    }

    @Override
    public void count(LogRecord log) {
        counter++;
    }

    @Override
    public Long get() {
        return counter;
    }
}

