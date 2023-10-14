package edu.hw2.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class PopularCommandExecutorTest {
    private record CloseCheckingConnection(boolean failing) implements Connection {
        private static final Set<CloseCheckingConnection> OPEN_CONNECTIONS = new HashSet<>();

        private CloseCheckingConnection(boolean failing) {
            this.failing = failing;
            OPEN_CONNECTIONS.add(this);
        }

        public static void assertAllClosed() {
            assertThat(OPEN_CONNECTIONS).isEmpty();
        }

        @Override
        public void close() {
            boolean removed = OPEN_CONNECTIONS.remove(this);
            if (!removed) {
                fail("Attempt of closing a closed connection");
            }
        }

        @Override
        public void execute(String command) {
            if (failing) {
                throw new ConnectionException("Failed to execute command");
            }
        }
    }

    private static class LastExeptionStoringConnection implements Connection {
        private static ConnectionException lastException;

        public static ConnectionException getLastException() {
            return lastException;
        }

        @Override
        public void close() {
        }

        @Override
        public void execute(String command) {
            ConnectionException exception = new ConnectionException("Failed to execute '" + command + "'");
            lastException = exception;
            throw exception;
        }
    }

    @Test
    @DisplayName("Null manager")
    void nullManager() {
        assertThrows(NullPointerException.class, () -> new PopularCommandExecutor(null, 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("Invalid max attempts")
    void invalidMaxAttempts(int maxAttempts) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PopularCommandExecutor(new DefaultConnectionManager(), maxAttempts)
        );
    }

    @Test
    @DisplayName("Connection is closed on success")
    void connectionClosedOnSuccess() {
        ConnectionManager stableConnectionManager = () -> new CloseCheckingConnection(false);
        PopularCommandExecutor executor = new PopularCommandExecutor(stableConnectionManager, 1);

        executor.tryExecute("rm -rf /");

        CloseCheckingConnection.assertAllClosed();
    }

    @Test
    @DisplayName("Connection is closed on failure")
    void connectionClosedOnFail() {
        ConnectionManager failingConnectionManager = () -> new CloseCheckingConnection(true);
        PopularCommandExecutor executor = new PopularCommandExecutor(failingConnectionManager, 1);

        try {
            executor.tryExecute("dd if=/dev/random of=/dev/sda");
        } catch (ConnectionException ignored) {
        }

        CloseCheckingConnection.assertAllClosed();
    }

    @Test
    @DisplayName("Not enough attempts")
    void notEnoughAttempts() {
        PopularCommandExecutor executor = new PopularCommandExecutor(new FaultyConnectionManager(), 2);

        assertThrows(ConnectionException.class, () -> executor.tryExecute("gunzip zipbomb.zip"));
    }

    @Test
    @DisplayName("Success after multiple attempts")
    void multipleAttemptsRequired() {
        PopularCommandExecutor executor = new PopularCommandExecutor(new FaultyConnectionManager(), 20);

        assertThatNoException().isThrownBy(() -> executor.tryExecute("chmod -R 777 /"));
    }

    @Test
    @DisplayName("Original exception stored")
    void originalExceptionStored() {
        PopularCommandExecutor executor = new PopularCommandExecutor(LastExeptionStoringConnection::new, 10);
        ConnectionException exception = null;

        try {
            executor.updatePackages();
        } catch (ConnectionException e) {
            exception = e;
        }

        assertThat(exception).isNotNull().cause().isEqualTo(LastExeptionStoringConnection.getLastException());
    }
}
