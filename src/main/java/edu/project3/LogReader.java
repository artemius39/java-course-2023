package edu.project3;

import java.util.Optional;
import java.util.stream.Stream;

public interface LogReader {
    Optional<Stream<String>> readLogs(String source);
}
