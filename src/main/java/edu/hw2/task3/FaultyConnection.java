package edu.hw2.task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int SUCCESS_FREQUENCY = 10;
    private int executionsUntilSuccess;

    public FaultyConnection() {
        executionsUntilSuccess = SUCCESS_FREQUENCY;
    }

    @Override
    public void execute(String command) {
        if (executionsUntilSuccess == 0) {
            executionsUntilSuccess = SUCCESS_FREQUENCY;
            LOGGER.info("Successfully executed '" + command + "'");
        } else {
            executionsUntilSuccess--;
            throw new ConnectionException("Failed to execute '" + command + "'");
        }
    }

    @Override
    public void close() {
        LOGGER.info("Closed connection");
    }
}
