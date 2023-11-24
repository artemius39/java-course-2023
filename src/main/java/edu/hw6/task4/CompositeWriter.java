package edu.hw6.task4;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public final class CompositeWriter {
    public static PrintWriter compositeWriter(Path path) {
        try {
            return new PrintWriter(
                    new OutputStreamWriter(
                            new BufferedOutputStream(
                                    new CheckedOutputStream(
                                            Files.newOutputStream(path),
                                            new Adler32()
                                    )
                            ),
                            StandardCharsets.UTF_8
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CompositeWriter() {
    }
}
