package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static edu.hw9.task2.FileFilters.directoriesWithMoreThanNFiles;
import static org.assertj.core.api.Assertions.assertThat;

class MoreThanNFilesTest {
    @Test
    @DisplayName("No directories found")
    void noDirectoriesFound(@TempDir Path tempDir) {
        Collection<Path> directoriesWithMoreThan1Element = directoriesWithMoreThanNFiles(tempDir, 1);

        assertThat(directoriesWithMoreThan1Element).isEmpty();
    }

    @Test
    @DisplayName("1 file")
    void oneFile(@TempDir Path tempDir) throws IOException {
        Files.createFile(tempDir.resolve("file"));

        Collection<Path> directoriesWithMoreThan1Element = directoriesWithMoreThanNFiles(tempDir, 1);

        assertThat(directoriesWithMoreThan1Element).contains(tempDir);
    }

    @Test
    @DisplayName("2 files")
    void twoFiles(@TempDir Path tempDir) throws IOException {
        Files.createFile(tempDir.resolve("file1"));
        Files.createFile(tempDir.resolve("file2"));

        Collection<Path> directoriesWithMoreThan1Element = directoriesWithMoreThanNFiles(tempDir, 1);

        assertThat(directoriesWithMoreThan1Element).contains(tempDir);
    }

    @Test
    @DisplayName("Multiple directories")
    void multipleDirectories(@TempDir Path tempDir) throws IOException {
        Path subdirectory1 = Files.createDirectory(tempDir.resolve("subdirectory1"));
        Files.createFile(subdirectory1.resolve("file"));
        Path subdirectory2 = Files.createDirectory(tempDir.resolve("subdirectory2"));
        Files.createFile(subdirectory2.resolve("file"));

        Collection<Path> directoriesWithMoreThan1Element = directoriesWithMoreThanNFiles(tempDir, 1);

        assertThat(directoriesWithMoreThan1Element).containsExactlyInAnyOrder(tempDir, subdirectory1, subdirectory2);
    }

    @Test
    @DisplayName("1000 files")
    void oneThousandFiles(@TempDir Path tempDir) throws IOException {
        for (int fileNo = 0; fileNo < 300; fileNo++) {
            Files.createFile(tempDir.resolve("file" + fileNo));
        }
        Path subdirectory1 = Files.createDirectory(tempDir.resolve("subdirectory1"));
        for (int fileNo = 0; fileNo < 200; fileNo++) {
            Files.createFile(subdirectory1.resolve("file" + fileNo));
        }
        Path subsubdirectory = Files.createDirectory(subdirectory1.resolve("subsubdirectory"));
        for (int fileNo = 0; fileNo < 200; fileNo++) {
            Files.createFile(subsubdirectory.resolve("file" + fileNo));
        }
        Path subdirectory2 = Files.createDirectory(tempDir.resolve("subdirectory2"));
        for (int fileNo = 0; fileNo < 100; fileNo++) {
            Files.createFile(subdirectory2.resolve("file" + fileNo));
        }
        Path subdirectory3 = Files.createDirectory(tempDir.resolve("subdirectory3"));
        for (int fileNo = 0; fileNo < 200; fileNo++) {
            Files.createFile(subdirectory3.resolve("file" + fileNo));
        }

        Collection<Path> directoriesWithMoreThan1Element = directoriesWithMoreThanNFiles(tempDir, 1000);

        assertThat(directoriesWithMoreThan1Element).containsExactlyInAnyOrder(tempDir);
    }
}
