package edu.project3;

import edu.project3.metrics.AverageResponseSize;
import edu.project3.metrics.Counter;
import edu.project3.metrics.Metric;
import edu.project3.metrics.ResourcesCounter;
import edu.project3.metrics.StatusCodeCounter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class MetricTest {
    private LogRecord withResponseSize(long responseSize) {
        return new LogRecord(
                null,
                null,
                null,
                null,
                null,
                0,
                responseSize,
                null,
                null
        );
    }

    private LogRecord withResource(String resource) {
        return new LogRecord(
                null,
                null,
                null,
                null,
                resource,
                0,
                0,
                null,
                null
        );
    }

    private LogRecord withStatusCode(int statusCode) {
        return new LogRecord(
                null,
                null,
                null,
                null,
                null,
                statusCode,
                0,
                null,
                null
        );
    }

    @Test
    @DisplayName("Counter")
    void counter() {
        Metric<Long> counter = new Counter();

        counter.count(null);
        counter.count(null);
        counter.count(null);
        counter.count(null);
        long count = counter.get();

        assertThat(count).isEqualTo(4);
    }

    @Test
    @DisplayName("Average response size")
    void averageResponseSize() {
        Metric<Long> metric = new AverageResponseSize();

        metric.count(withResponseSize(10));
        metric.count(withResponseSize(20));
        metric.count(withResponseSize(30));
        metric.count(withResponseSize(0));
        long averageResponseSize = metric.get();

        assertThat(averageResponseSize).isEqualTo(15);
    }

    @Test
    @DisplayName("Average response size with no logs")
    void averageResponseSizeWithNoLogs() {
        Metric<Long> metric = new AverageResponseSize();

        long averageResponseSize = metric.get();

        assertThat(averageResponseSize).isZero();
    }

    @Test
    @DisplayName("ResourcesCounter")
    void resourcesCounter() {
        Metric<Map<String, Integer>> metric = new ResourcesCounter();

        metric.count(withResource("1"));
        metric.count(withResource("1"));
        metric.count(withResource("2"));
        metric.count(withResource("3"));
        metric.count(withResource("3"));
        metric.count(withResource("1"));
        Map<String, Integer> resources = metric.get();

        assertThat(resources).containsEntry("1", 3)
                .containsEntry("2", 1)
                .containsEntry("3", 2);
    }

    @Test
    @DisplayName("Status code counter")
    void statusCodeCounter() {
        Metric<Map<Integer, Integer>> metric = new StatusCodeCounter();

        metric.count(withStatusCode(200));
        metric.count(withStatusCode(200));
        metric.count(withStatusCode(304));
        metric.count(withStatusCode(404));
        metric.count(withStatusCode(500));
        Map<Integer, Integer> statusCodeFrequencies = metric.get();

        assertThat(statusCodeFrequencies).containsEntry(200, 2)
                .containsEntry(304, 1)
                .containsEntry(404, 1)
                .containsEntry(500, 1);
    }
}
