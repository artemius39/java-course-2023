package edu.hw3.task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Task3 {
    private Task3() {
    }

    public static <E> Map<E, Integer> freqDict(List<E> elements) {
        Objects.requireNonNull(elements);
        Map<E, Integer> freqDict = new HashMap<>();
        for (E element : elements) {
            freqDict.put(element, freqDict.getOrDefault(element, 0) + 1);
        }
        return freqDict;
    }
}
