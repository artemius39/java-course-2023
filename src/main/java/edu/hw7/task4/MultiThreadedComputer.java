package edu.hw7.task4;

import java.util.concurrent.CountDownLatch;
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
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int thread = 0; thread < threadCount; thread++) {
            new Thread(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                int threadLocalPointsWithinCircle = countPointsWithinCircle(iterationsPerThread, random);
                pointsWithinCircle.addAndGet(threadLocalPointsWithinCircle);
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return getPi(iterationCountAdjusted, pointsWithinCircle.get());
    }
}
