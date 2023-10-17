package edu.hw2.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FaultyConnectionManagerTest {
    @Test
    @DisplayName("Always faulty")
    void alwaysFaulty() {
        boolean allFaulty = true;
        ConnectionManager connectionManager = new FaultyConnectionManager();

        for (int connectionCount = 0; connectionCount < 100; connectionCount++) {
            Connection connection = connectionManager.getConnection();
            allFaulty = allFaulty && connection instanceof FaultyConnection;
        }

        assertThat(allFaulty).isTrue();
    }
}
