package edu.hw6.task2;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static edu.hw6.task2.CloneFile.cloneFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CloneFileTest {
    @BeforeEach
    void createTemporaryDirectory() {
        try {
            Files.createDirectory(Path.of(".tmp"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void deleteTemporaryDirectory() {
        try {
            FileUtils.deleteDirectory(new File(".tmp"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("One copy")
    void oneCopy() {
        Path biggestSecret;
        try {
            biggestSecret = Files.createFile(Path.of(".tmp", "Tinkoff Bank Biggest Secret.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path clone = cloneFile(biggestSecret);

        assertThat(clone)
                .exists()
                .hasFileName("Tinkoff Bank Biggest Secret - copy.txt");
    }

    @Test
    @DisplayName("Multiple copies")
    void multipleCopies() {
        Path biggestSecret;

        try {
            biggestSecret = Files.createFile(Path.of(".tmp", "Tinkoff Bank Biggest Secret.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path clone0 = cloneFile(biggestSecret);
        Path clone1 = cloneFile(biggestSecret);
        Path clone2 = cloneFile(biggestSecret);

        assertThat(clone0)
                .exists()
                .hasFileName("Tinkoff Bank Biggest Secret - copy.txt");
        assertThat(clone1)
                .exists()
                .hasFileName("Tinkoff Bank Biggest Secret - copy (1).txt");
        assertThat(clone2)
                .exists()
                .hasFileName("Tinkoff Bank Biggest Secret - copy (2).txt");
    }

    @Test
    @DisplayName("File without extension")
    void fileWithoutExtension() {
        Path path;

        try {
            path = Files.createFile(Path.of(".tmp", "Tinkoff Bank Biggest Secret"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path clone = cloneFile(path);

        assertThat(clone)
                .exists()
                .hasFileName("Tinkoff Bank Biggest Secret - copy");
    }

    @Test
    @DisplayName("Source file doesn't exist")
    void sourceFileDoesntExist() {
        assertThrows(RuntimeException.class, () -> cloneFile(Path.of("[REDACTED]")));
    }
}
