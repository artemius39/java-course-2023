package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public final class FileFilters {
    public static Collection<Path> directoriesWithMoreThanNFiles(Path path, int n) {
        Objects.requireNonNull(path, "path cannot be null");
        Collection<Path> directoriesWithMoreThanNFiles = new ConcurrentLinkedQueue<>();
        new DirectoriesWithMoreThanNFilesTask(path, n, directoriesWithMoreThanNFiles).fork().join();
        return directoriesWithMoreThanNFiles;
    }

    public static Collection<Path> filter(Path path, Predicate<Path> predicate) {
        Objects.requireNonNull(path, "path cannot be null");
        Objects.requireNonNull(predicate, "predicate cannot be null");
        Collection<Path> result = new ConcurrentLinkedQueue<>();
        new FilterTask(path, predicate, result).fork().join();
        return result;
    }

    private FileFilters() {
    }

    private static class DirectoriesWithMoreThanNFilesTask extends RecursiveTask<Integer> {
        private final Path path;
        private final int targetFileCount;
        private final Collection<Path> directoriesWithMoreThanNFiles;

        private DirectoriesWithMoreThanNFilesTask(Path path, int targetFileCount,
                                                  Collection<Path> directoriesWithMoreThanNFiles) {
            this.path = path;
            this.targetFileCount = targetFileCount;
            this.directoriesWithMoreThanNFiles = directoriesWithMoreThanNFiles;
        }

        @Override
        protected Integer compute() {
            if (Files.isRegularFile(path)) {
                return 1;
            }

            List<DirectoriesWithMoreThanNFilesTask> tasks = new ArrayList<>();
            try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
                for (Path file : files) {
                    DirectoriesWithMoreThanNFilesTask task =
                            new DirectoriesWithMoreThanNFilesTask(file, targetFileCount, directoriesWithMoreThanNFiles);
                    tasks.add(task);
                    task.fork();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int totalFiles = 0;
            for (DirectoriesWithMoreThanNFilesTask task : tasks.reversed()) {
                totalFiles += task.join();
            }
            if (totalFiles >= targetFileCount) {
                directoriesWithMoreThanNFiles.add(path);
            }

            return totalFiles;
        }
    }

    private static class FilterTask extends RecursiveTask<Void> {
        private final Path path;
        private final Predicate<Path> predicate;
        private final Collection<Path> result;

        FilterTask(Path path, Predicate<Path> predicate, Collection<Path> result) {
            this.path = path;
            this.predicate = predicate;
            this.result = result;
        }

        @Override
        protected Void compute() {
            if (Files.isRegularFile(path)) {
                if (predicate.test(path)) {
                    result.add(path);
                }
                return null;
            }

            List<FilterTask> tasks = new ArrayList<>();
            try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
                for (Path file : files) {
                    FilterTask task = new FilterTask(file, predicate, result);
                    task.fork();
                    tasks.add(task);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            tasks.reversed().forEach(ForkJoinTask::join);

            return null;
        }
    }
}
