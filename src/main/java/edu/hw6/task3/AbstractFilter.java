package edu.hw6.task3;

import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.MagicNumberFileFilter;

public interface AbstractFilter extends Filter<Path> {
    AbstractFilter READABLE = Files::isReadable;
    AbstractFilter WRITABLE = Files::isWritable;
    AbstractFilter EXECUTABLE = Files::isExecutable;
    AbstractFilter REGULAR = Files::isRegularFile;
    AbstractFilter DIRECTORY = Files::isDirectory;

    default AbstractFilter and(AbstractFilter other) {
        return file -> this.accept(file) && other.accept(file);
    }

    default AbstractFilter or(AbstractFilter other) {
        return file -> this.accept(file) || other.accept(file);
    }

    default AbstractFilter negate() {
        return file -> !this.accept(file);
    }

    static AbstractFilter largerThan(long size) {
        return file -> sizeOf(file) > size;
    }

    static AbstractFilter smallerThan(long size) {
        return file -> sizeOf(file) < size;
    }

    static AbstractFilter hasSize(long size) {
        return file -> sizeOf(file) == size;
    }

    static AbstractFilter hasExtension(String extension) {
        return file -> FilenameUtils.getExtension(getNameString(file)).equals(extension);
    }

    static AbstractFilter nameMatchesRegex(String regex) {
        return file -> getNameString(file).matches(regex);
    }

    static AbstractFilter nameContainsRegex(String regex) {
        return nameMatchesRegex(".*" + regex + ".*");
    }

    static AbstractFilter magicNumber(byte... bytes) {
        return file -> new MagicNumberFileFilter(bytes).accept(file.toFile());
    }

    static AbstractFilter globMatches(String glob) {
        return file -> FileSystems.getDefault().getPathMatcher("glob:" + glob).matches(file);
    }

    private static String getNameString(Path file) {
        return file.getFileName().toString();
    }

    private static long sizeOf(Path file) {
        return file.toFile().length();
    }
}
