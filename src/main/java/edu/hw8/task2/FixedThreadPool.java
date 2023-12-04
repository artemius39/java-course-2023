package edu.hw8.task2;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

class FixedThreadPool implements ThreadPool {
    private final Thread[] threads;
    private final BlockingQueue<Runnable> tasks;
    private final AtomicBoolean running;

    @Override
    public void start() {
        running.set(true);
        Arrays.stream(threads).forEach(Thread::start);
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            if (!tasks.offer(runnable, 1, TimeUnit.SECONDS)) {
                throw new RejectedExecutionException();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        running.set(false);
        for (Thread thread : threads) {
            try {
                if (!thread.join(Duration.ofSeconds(10))) {
                    thread.interrupt();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    FixedThreadPool(int threadCount) {
        tasks = new ArrayBlockingQueue<>(threadCount);
        running = new AtomicBoolean();
        threads = Stream.generate(() -> new Thread(() -> {
                    while (running.get() || !tasks.isEmpty()) {
                        try {
                            Runnable task = tasks.poll(1, TimeUnit.SECONDS);
                            if (task != null) {
                                task.run();
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }))
                .limit(threadCount)
                .toArray(Thread[]::new);
    }
}
