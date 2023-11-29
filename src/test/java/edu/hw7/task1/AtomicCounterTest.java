package edu.hw7.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.assertThat;

class AtomicCounterTest {
    @Test
    @DisplayName("Initial state is zero")
    void initialStateIsZero() {
        AtomicCounter counter = new AtomicCounter();

        int value = counter.get();

        assertThat(value).isZero();
    }

    @Test
    @DisplayName("Single-threaded increment")
    void singleThreadedIncrement() {
        AtomicCounter counter = new AtomicCounter();

        for (int iteration = 0; iteration < 100; iteration++) {
            counter.increment();
        }
        int value = counter.get();

        assertThat(value).isEqualTo(100);
    }

    @Test
    @DisplayName("Multi-threaded increment")
    void noRaceConditionInMultiThreadedMode() {
        AtomicCounter counter = new AtomicCounter();

        try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            for (int thread = 0; thread < 10; thread++) {
                executorService.submit(() -> {
                    for (int iteration = 0; iteration < 10_000; iteration++) {
                        counter.increment();
                    }
                });
            }
        }
        int value = counter.get();

        assertThat(value).isEqualTo(100_000);
    }
}
