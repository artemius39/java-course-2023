package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static edu.hw9.task2.FileFilters.filter;
import static org.assertj.core.api.Assertions.assertThat;

class FilterTest {
    @Test
    @DisplayName("Empty directory")
    void emptyDirectoryReturnsEmptyCollection(@TempDir Path tempDir) {
        Collection<Path> result = filter(tempDir, path -> true);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("File inside directory")
    void fileInsideDirectoryIsFound(@TempDir Path tempDir) throws IOException {
        Path file = Files.createFile(tempDir.resolve("file"));

        Collection<Path> result = filter(tempDir, path -> path.getFileName().toString().equals("file"));

        assertThat(result).contains(file);
    }

    @Test
    @DisplayName("File in subdirectory")
    void filesInSubdirectoriesAreAlsoTested(@TempDir Path tempDir) throws IOException {
        Path subdirectory = Files.createDirectory(tempDir.resolve("subdirectory"));
        Path file = Files.createFile(subdirectory.resolve("file"));

        Collection<Path> result = filter(tempDir, path -> path.getFileName().toString().equals("file"));

        assertThat(result).contains(file);
    }

    @Test
    @DisplayName("Not all files match predicate")
    void filesThatDontMatchPredicateAreNotReturned(@TempDir Path tempDir) throws IOException {
        Path matches = Files.createFile(tempDir.resolve("matches"));
        Path doesntMatch = Files.createFile(tempDir.resolve("doesn't match"));

        Collection<Path> result = filter(tempDir, path -> path.getFileName().toString().equals("matches"));

        assertThat(result).contains(matches)
                .doesNotContain(doesntMatch);
    }

    @Test
    @DisplayName("Multiple files match predicate")
    void allMatchingFilesAreReturned(@TempDir Path tempDir) throws IOException {
        Path matches = Files.createFile(tempDir.resolve("matches"));
        Path alsoMatches = Files.createFile(tempDir.resolve("also matches"));

        Collection<Path> result = filter(tempDir, path -> true);

        assertThat(result).containsExactlyInAnyOrder(matches, alsoMatches);
    }
}
