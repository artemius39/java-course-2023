package edu.project3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class PathLogReaderTest {
    @Test
    @DisplayName("Relative path")
    void relativePath() {
        LogReader reader = new PathLogReader();
        String source = Path.of("src", "test", "resources", "project3", "file1").toString();

        Optional<Stream<String>> logs = reader.readLogs(source);

        assertThat(logs).isPresent();
        assertThat((Iterable<String>) () -> logs.get().iterator()).contains("file1");
    }

    @Test
    @DisplayName("Absolute path")
    void absolutePath() {
        LogReader reader = new PathLogReader();
        String source = Path.of("src", "test", "resources", "project3", "file1").toAbsolutePath().toString();

        Optional<Stream<String>> logs = reader.readLogs(source);

        assertThat(logs).isPresent();
        assertThat((Iterable<String>) () -> logs.get().iterator()).contains("file1");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "doesnt-exist", "https://youtu.be/PkT0PJwy8mI?si=CVQSz3SOFu-4npgu", ".."})
    @DisplayName("Invalid file")
    void invalidFile(String source) {
        LogReader reader = new PathLogReader();

        Optional<Stream<String>> logs = reader.readLogs(source);

        assertThat(logs).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("Wildcards")
    @ValueSource(strings = {"file?", "f*", "file{1,2}"})
    void wildcards(String wildcard) {
        LogReader reader = new PathLogReader();
        String source = Path.of("src", "test", "resources", "project3", wildcard).toString();

        Optional<Stream<String>> logs = reader.readLogs(source);

        assertThat(logs).isPresent();
        assertThat(logs.get().toList()).containsExactlyInAnyOrder("file1", "file2");
    }
}