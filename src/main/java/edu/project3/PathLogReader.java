package edu.project3;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class PathLogReader implements LogReader {
    @Override
    public Optional<Stream<String>> readLogs(String source) {
        List<Path> paths = new ArrayList<>();
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + Path.of(source).toAbsolutePath());

        try {
            PathMatchingVisitor visitor = new PathMatchingVisitor(paths, matcher);
            Files.walkFileTree(Path.of(System.getProperty("user.dir")), visitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return paths.stream()
                .map(path -> {
                    try {
                        return Files.lines(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .reduce(Stream::concat);
    }

    private static class PathMatchingVisitor extends SimpleFileVisitor<Path> {
        private final List<Path> paths;
        private final PathMatcher matcher;

        private PathMatchingVisitor(List<Path> paths, PathMatcher matcher) {
            this.paths = paths;
            this.matcher = matcher;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (matcher.matches(file)) {
                paths.add(file);
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
