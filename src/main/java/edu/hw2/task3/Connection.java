package edu.hw2.task3;

public interface Connection extends AutoCloseable {
    @Override
    void close();

    void execute(String command);
}

