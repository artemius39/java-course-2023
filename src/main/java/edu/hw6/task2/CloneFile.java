package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.apache.commons.io.FilenameUtils;

public final class CloneFile {
    public static Path cloneFile(Path path) {
        String fileName = path.getFileName().toString();
        String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        if (!extension.isEmpty()) {
            extension = FilenameUtils.EXTENSION_SEPARATOR_STR + extension;
        }

        try {
            return Files.copy(path, path.resolveSibling(fileNameWithoutExtension + " - copy" + extension));
        } catch (NoSuchFileException e) {
            throw new IllegalArgumentException("cannot copy nonexistent file: " + path, e);
        } catch (FileAlreadyExistsException e) {
            return cloneFile(path, 1, fileNameWithoutExtension, extension);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path cloneFile(Path path, long index, String fileNameWithoutExtension, String extension) {
        String copyName = "%s - copy (%d)%s".formatted(fileNameWithoutExtension, index, extension);
        try {
            return Files.copy(path, path.resolveSibling(copyName));
        } catch (NoSuchFileException e) {
            throw new IllegalArgumentException("cannot clone nonexistent file: " + path, e);
        } catch (FileAlreadyExistsException e) {
            return cloneFile(path, index + 1, fileNameWithoutExtension, extension);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CloneFile() {
    }
}
