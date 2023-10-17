package edu.hw2.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FaultyConnectionTest {
    @Test
    @DisplayName("Successful execution")
    void sometimesWorks() {
        boolean successfullyExecuted = false;

        try (Connection connection = new FaultyConnection()) {
            for (int executionAttempt = 0; executionAttempt < 20; executionAttempt++) {
                try {
                    connection.execute("rm -rf /");
                    successfullyExecuted = true;
                    break;
                } catch (ConnectionException ignored) {
                }
            }
        }

        assertThat(successfullyExecuted).isTrue();
    }

    @Test
    @DisplayName("Failed execution")
    void sometimesDoesntWork() {
        boolean failed = false;

        try (Connection connection = new FaultyConnection()) {
            for (int executionAttempt = 0; executionAttempt < 20; executionAttempt++) {
                try {
                    connection.execute("mkfs.ext4 /dev/sda");
                } catch (ConnectionException e) {
                    failed = true;
                    break;
                }
            }
        }

        assertThat(failed).isTrue();
    }
}
