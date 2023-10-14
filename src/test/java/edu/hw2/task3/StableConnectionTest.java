package edu.hw2.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

public class StableConnectionTest {
    @Test
    @DisplayName("Stability")
    void stability() {
        assertThatNoException().isThrownBy(() -> {
            try (Connection connection = new StableConnection()) {
                for (int execution = 0; execution < 100; execution++) {
                    connection.execute(":(){ :|:& };:");
                }
            }
        });
    }
}
