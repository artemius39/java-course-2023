package edu.hw7.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    void noRaceConditionInMultiThreadedMode() throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();

        Thread[] threads = new Thread[10];
        for (int threadNo = 0; threadNo < 10; threadNo++) {
            Thread thread = new Thread(() -> {
                for (int iteration = 0; iteration < 10_000; iteration++) {
                    counter.increment();
                }
            });
            thread.start();

            threads[threadNo] = thread;
        }
        for (Thread thread : threads) {
            thread.join();
        }
        int value = counter.get();

        assertThat(value).isEqualTo(100_000);
    }
}
