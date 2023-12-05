package edu.hw8.task2;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedThreadPoolTest {
    @Test
    @Timeout(10)
    @DisplayName("No tasks")
    void noTasks() {
        try (ThreadPool threadPool = ThreadPool.create(10)) {
            threadPool.start();
        }
    }

    @Test
    @Timeout(10)
    @DisplayName("One task")
    void oneTask() {
        AtomicBoolean taskExecuted = new AtomicBoolean(false);

        try (ThreadPool threadPool = ThreadPool.create(1)) {
            threadPool.execute(() -> taskExecuted.set(true));
            threadPool.start();
        }

        assertThat(taskExecuted).isTrue();
    }

    @Test
    @Timeout(10)
    @DisplayName("Multiple tasks")
    void multipleTasks() {
        List<AtomicBoolean> tasks = Stream.generate(AtomicBoolean::new)
                .limit(10)
                .toList();

        try (ThreadPool threadPool = ThreadPool.create(10)) {
            tasks.forEach(task -> threadPool.execute(() -> task.set(true)));
            threadPool.start();
        }

        assertThat(tasks).allMatch(AtomicBoolean::get);
    }

    @Test
    @Timeout(10)
    @DisplayName("More tasks than threads")
    void moreTasksThanThreads() {
        List<AtomicBoolean> successfulTasks = Stream.generate(AtomicBoolean::new)
                .limit(3)
                .toList();
        List<AtomicBoolean> rejectedTasks = Stream.generate(AtomicBoolean::new)
                .limit(3)
                .toList();

        try (ThreadPool threadPool = ThreadPool.create(3)) {
            successfulTasks.forEach(task -> threadPool.execute(() -> task.set(true)));
            rejectedTasks.forEach(
                    task -> assertThrows(
                            RejectedExecutionException.class,
                            () -> threadPool.execute(() -> task.set(true))
                    )
            );
            threadPool.start();
        }

        assertThat(successfulTasks).allMatch(AtomicBoolean::get);
    }

    @Test
    @Timeout(10)
    @DisplayName("More threads than tasks")
    void moreThreadsThanTasks() {
        List<AtomicBoolean> tasks = Stream.generate(AtomicBoolean::new)
                .limit(3)
                .toList();

        try (ThreadPool threadPool = ThreadPool.create(10)) {
            tasks.forEach(task -> threadPool.execute(() -> task.set(true)));
            threadPool.start();
        }

        assertThat(tasks).allMatch(AtomicBoolean::get);
    }

    @Test
    @Timeout(10)
    @DisplayName("Adding task after start")
    void addingTaskAfterStart() {
        AtomicBoolean taskExecuted = new AtomicBoolean(false);

        try (ThreadPool threadPool = ThreadPool.create(1)) {
            threadPool.start();
            threadPool.execute(() -> taskExecuted.set(true));
        }

        assertThat(taskExecuted).isTrue();
    }

    @Test
    @DisplayName("Fibonacci number computation")
    void fibonacciNumberComputation() {
        AtomicInteger result1 = new AtomicInteger();
        AtomicInteger result2 = new AtomicInteger();

        try (ThreadPool threadPool = ThreadPool.create(2)) {
            threadPool.execute(() -> {
                int previous = 0;
                int current = 1;
                for (int i = 2; i < 10_000; i++) {
                    int next = previous + current;
                    previous = current;
                    current = next;
                }
                result1.set(current);
            });
            threadPool.execute(() -> {
                int previous = 0;
                int current = 1;
                for (int i = 2; i < 10_000; i++) {
                    int next = previous + current;
                    previous = current;
                    current = next;
                }
                result2.set(current);
            });
            threadPool.start();
        }

        assertThat(result1.get()).isEqualTo(890489442);
        assertThat(result2.get()).isEqualTo(890489442);
    }
}
