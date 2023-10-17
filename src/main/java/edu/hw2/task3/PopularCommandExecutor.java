package edu.hw2.task3;

import java.util.Objects;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        Objects.requireNonNull(manager);
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("Max attempt count must be positive");
        }

        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    public void tryExecute(String command) {
        Objects.requireNonNull(command);
        ConnectionException lastException = null;

        try (Connection connection = manager.getConnection()) {
            for (int attemptCount = 0; attemptCount < maxAttempts; attemptCount++) {
                try {
                    connection.execute(command);
                    return;
                } catch (ConnectionException e) {
                    lastException = e;
                }
            }
        }

        throw new ConnectionException(
                "Failed to execute '%s' after %d attempts".formatted(command, maxAttempts),
                lastException
        );
    }
}
