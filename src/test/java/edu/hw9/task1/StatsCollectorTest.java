package edu.hw9.task1;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatsCollectorTest {
    private static final Offset<Double> OFFSET = Offset.offset(1e-9);
    private void assertStat(Map<String, Statistic> stats, String name, Statistic expected) {
        assertThat(stats).containsKey(name);

        Statistic actual = stats.get(name);
        assertThat(actual.max()).isCloseTo(expected.max(), OFFSET);
        assertThat(actual.min()).isCloseTo(expected.min(), OFFSET);
        assertThat(actual.sum()).isCloseTo(expected.sum(), OFFSET);
        assertThat(actual.average()).isCloseTo(expected.average(), OFFSET);
    }

    @Test
    @DisplayName("No pushes")
    void noPushesShouldReturnEmptyMap() {
        StatsCollector statsCollector = new StatsCollector();

        Map<String, Statistic> stats = statsCollector.stats();

        assertThat(stats).isEmpty();
    }

    @Test
    @DisplayName("One push")
    void onePush() {
        StatsCollector statsCollector = new StatsCollector();

        statsCollector.push("Metric 1", new double[] {1, 2, 3});
        Map<String, Statistic> stats = statsCollector.stats();

        assertStat(stats, "Metric 1", new Statistic(3, 1, 6, 2));
    }

    @Test
    @DisplayName("Multiple pushes")
    void multiplePushes() {
        StatsCollector statsCollector = new StatsCollector();

        statsCollector.push("Metric 1", new double[] {-1, 0, 0});
        statsCollector.push("Metric 2", new double[] {10, 1.5, 0.1});
        statsCollector.push("Metric 3", new double[] {0.1111, 1.2222, -0.3333, 0});
        Map<String, Statistic> stats = statsCollector.stats();

        assertStat(stats, "Metric 1", new Statistic(0, -1, -1, -1.0 / 3));
        assertStat(stats, "Metric 2", new Statistic(10, 0.1, 11.6, 11.6 / 3));
        assertStat(stats, "Metric 3", new Statistic(1.2222, -0.3333, 1, 1.0 / 4));
    }

    @Test
    @DisplayName("Concurrent pushes")
    void concurrentPushes() {
        StatsCollector statsCollector = new StatsCollector();
        double[] lotsOfData = new double[10_000_000];

        try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            for (int thread = 0; thread < 10; thread++) {
                int currentThread = thread;
                executorService.submit(() -> statsCollector.push("Metric " + currentThread, lotsOfData));
            }
        }
        Map<String, Statistic> stats = statsCollector.stats();

        for (int thread = 0; thread < 10; thread++) {
            assertStat(stats, "Metric " + thread, new Statistic(0, 0, 0, 0));
        }
    }
}
