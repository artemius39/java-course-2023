package edu.project3.metrics;

import edu.project3.LogRecord;

public class AverageResponseSize implements Metric<Long> {
    private long sizes;
    private long requests;

    public AverageResponseSize() {
        sizes = 0;
        requests = 0;
    }

    @Override
    public void count(LogRecord log) {
        sizes += log.bodyBytesSent();
        requests++;
    }

    @Override
    public Long get() {
        return requests == 0 ? sizes : sizes / requests;
    }
}
