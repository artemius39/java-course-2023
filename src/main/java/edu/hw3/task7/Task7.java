package edu.hw3.task7;

import java.util.Comparator;

public final class Task7 {
    public static <T extends Comparable<? super T>> Comparator<T> nullFriendlyComparator() {
        return nullFriendlyComparator(Comparator.<T>naturalOrder());
    }

    public static <T> Comparator<T> nullFriendlyComparator(Comparator<T> comparator) {
        return Comparator.nullsFirst(comparator);
    }

    private Task7() {
    }
}
