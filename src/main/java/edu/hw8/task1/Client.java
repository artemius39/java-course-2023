package edu.hw8.task1;

import java.io.IOException;

public interface Client extends AutoCloseable {
    @Override
    void close() throws IOException;

    String waitResponse() throws IOException;

    void send(String keyword) throws IOException;
}

