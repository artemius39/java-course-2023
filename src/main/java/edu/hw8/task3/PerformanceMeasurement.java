package edu.hw8.task3;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"UncommentedMain", "MagicNumber", "RegexpSinglelineJava"})
public final class PerformanceMeasurement {
    public static void main(String[] args) {
        Map<String, String> database = Map.of(
                "e10adc3949ba59abbe56e057f20f883e", "a.v.petrov",
                "d8578edf8458ce06fbc5bb76a58c5ca4", "v.v.belov",
                "482c811da5d5b4bc6d497ffa98491e38", "a.s.ivanov",
                "5f4dcc3b5aa765d61d8327deb882cf99", "k.p.maslov"
        );

        List<Character> alphabet = Stream.of(
                        IntStream.rangeClosed('a', 'z'),
                        IntStream.rangeClosed('A', 'Z'),
                        IntStream.rangeClosed('0', '9'))
                .reduce(IntStream::concat)
                .orElseThrow()
                .mapToObj(i -> (char) i)
                .toList();

        int maxLength = 4;
        System.out.println("Max password length: " + maxLength);

        long singleThreadedTime = measure(new SingleThreadedPasswordCracker(alphabet), database, maxLength);
        System.out.println("Single-threaded time: " + singleThreadedTime + "ms");

        long multiThreadedTime = measure(new MultiThreadedPasswordCracker(alphabet), database, maxLength);
        System.out.println("Multi-threaded time: " + multiThreadedTime + "ms");

        System.out.println("Speed increase: " + (double) singleThreadedTime / multiThreadedTime + " times");
    }

    private static long measure(PasswordCracker cracker, Map<String, String> database, int maxLength) {
        long startTime = System.nanoTime();
        cracker.crack(database, maxLength);
        return (System.nanoTime() - startTime) / 1_000_000;
    }

    private PerformanceMeasurement() {
    }
}
