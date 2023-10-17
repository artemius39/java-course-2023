package edu.hw2.task3;

public class DefaultConnectionManager implements ConnectionManager {
    private int connectionsUntilFaulty;
    private static final int FAULTY_FREQUENCY = 5;

    public DefaultConnectionManager() {
        connectionsUntilFaulty = FAULTY_FREQUENCY;
    }

    @Override
    public Connection getConnection() {
        if (connectionsUntilFaulty == 0) {
            connectionsUntilFaulty = FAULTY_FREQUENCY;
            return new FaultyConnection();
        } else {
            connectionsUntilFaulty--;
            return new StableConnection();
        }
    }
}
