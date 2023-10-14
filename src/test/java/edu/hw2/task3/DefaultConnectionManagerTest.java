package edu.hw2.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultConnectionManagerTest {
    @Test
    @DisplayName("Stable connection")
    void stable() {
        ConnectionManager connectionManager = new DefaultConnectionManager();
        boolean gotStable = false;

        for (int connectionCount = 0; connectionCount < 10; connectionCount++) {
            Connection connection = connectionManager.getConnection();
            if (connection instanceof StableConnection) {
                gotStable = true;
                break;
            }
        }

        assertThat(gotStable).isTrue();
    }

    @Test
    @DisplayName("Faulty connection")
    void faulty() {
        ConnectionManager connectionManager = new DefaultConnectionManager();
        boolean gotFaulty = false;

        for (int connectionCount = 0; connectionCount < 10; connectionCount++) {
            Connection connection = connectionManager.getConnection();
            if (connection instanceof FaultyConnection) {
                gotFaulty = true;
                break;
            }
        }

        assertThat(gotFaulty).isTrue();
    }
}
