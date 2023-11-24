package edu.hw6.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import static edu.hw6.task4.CompositeWriter.compositeWriter;
import static org.assertj.core.api.Assertions.assertThat;

class CompositeWriterTest {
    @Test
    @DisplayName("New file")
    void newFile() throws IOException {
        Path destination = Path.of(".tmp");
        try (PrintWriter writer = compositeWriter(destination)) {
            writer.print("Programming is learned by writing programs. ― Brian Kernighan");
        }

        assertThat(destination)
                .exists()
                .content(StandardCharsets.UTF_8)
                .isEqualTo("Programming is learned by writing programs. ― Brian Kernighan");

        Files.delete(destination);
    }
}
