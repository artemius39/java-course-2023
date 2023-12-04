package edu.hw9.task1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatsCollector {
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executorService;
    private final BlockingQueue<Metric> tasks;
    private final List<Future<Map<String, Statistic>>> futureStats;

    public StatsCollector() {
        tasks = new LinkedBlockingQueue<>();
        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        futureStats = Stream.generate(() -> executorService.submit(() -> {
                    Map<String, Statistic> stats = new HashMap<>();
                    while (!executorService.isShutdown() || !tasks.isEmpty()) {
                        Metric metric;
                        try {
                            metric = tasks.poll(1, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        if (metric != null) {
                            stats.put(metric.getName(), metric.compute());
                        }
                    }
                    return stats;
                }))
                .limit(THREAD_COUNT)
                .toList();
    }

    public void push(String metricName, double[] data) {
        if (executorService.isShutdown()) {
            throw new IllegalStateException("No more tasks accepted");
        }
        try {
            tasks.put(new Metric(metricName, data));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Statistic> stats() {
        executorService.close();
        return futureStats.stream()
                .map(mapFuture -> {
                    try {
                        return mapFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
