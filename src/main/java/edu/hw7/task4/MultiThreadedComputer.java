package edu.hw7.task4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadedComputer extends MonteCarloComputer {
    private final int threadCount;

    public MultiThreadedComputer(int threadCount) {
        this.threadCount = threadCount;
    }

    public MultiThreadedComputer() {
        threadCount = Runtime.getRuntime().availableProcessors();
    }

    @Override
    public double compute(int iterationCount) {
        int iterationsPerThread = Math.ceilDiv(iterationCount, threadCount);
        int iterationCountAdjusted = threadCount * iterationsPerThread;

        AtomicInteger pointsWithinCircle = new AtomicInteger(0);
        try (ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
            for (int thread = 0; thread < threadCount; thread++) {
                executorService.submit(() -> {
                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    int threadLocalPointsWithinCircle = countPointsWithinCircle(iterationsPerThread, random);
                    pointsWithinCircle.addAndGet(threadLocalPointsWithinCircle);
                });
            }
        }

        return getPi(iterationCountAdjusted, pointsWithinCircle.get());
    }
}
