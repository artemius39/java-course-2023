package edu.hw8.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class MultiThreadedPasswordCracker extends BasePasswordCracker {
    private final int threads;

    public MultiThreadedPasswordCracker(List<Character> alphabet) {
        this(alphabet, Runtime.getRuntime().availableProcessors());
    }

    public MultiThreadedPasswordCracker(List<Character> alphabet, int threads) {
        super(alphabet);
        this.threads = threads;
    }

    @Override
    public Map<String, String> crack(Map<String, String> database, int maxLength) {
        long totalPasswords = passwordsWithLengthUpTo(maxLength);
        long passwordsPerThread = Math.ceilDiv(totalPasswords, threads);

        List<Future<Map<String, String>>> tasks = new ArrayList<>();
        try (ExecutorService executorService = Executors.newFixedThreadPool(threads)) {
            for (int thread = 0; thread < threads; thread++) {
                PasswordGenerator generator = new BruteForcePasswordGenerator(
                        alphabet,
                        passwordsPerThread * thread,
                        Math.min(passwordsPerThread * (thread + 1), totalPasswords)
                );
                tasks.add(executorService.submit(() -> crackImpl(generator, database)));
            }
        }

        return mergeResults(tasks);
    }

    private Map<String, String> mergeResults(List<Future<Map<String, String>>> tasks) {
        return tasks.stream()
                .map(task -> {
                    try {
                        return task.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
