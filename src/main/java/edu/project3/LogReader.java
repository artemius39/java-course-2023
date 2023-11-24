package edu.project3;

import java.util.Optional;
import java.util.stream.Stream;

public interface LogReader extends AutoCloseable {
    Optional<Stream<String>> readLogs(String source);

    @Override
    void close();
}
