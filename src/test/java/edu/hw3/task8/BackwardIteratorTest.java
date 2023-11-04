package edu.hw3.task8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BackwardIteratorTest {
    @Test
    @DisplayName("Null collection")
    void nullCollection() {
        assertThrows(NullPointerException.class, () -> new BackwardIterator<>(null));
    }

    @Test
    @DisplayName("Empty collection")
    void emptyCollection() {
        Iterable<String> iterable = () -> new BackwardIterator<>(List.of());

        assertThat(iterable).isEmpty();
    }

    static Stream<Arguments> variousCollections() {
        return Stream.of(
                Arguments.of(new ArrayList<>()),
                Arguments.of(new TreeSet<>()),
                Arguments.of(new LinkedHashSet<>()),
                Arguments.of(new ArrayDeque<>())
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Various collections")
    void variousCollections(Collection<Integer> collection) {
        collection.add(1);
        collection.add(2);
        collection.add(3);
        Iterable<Integer> iterable = () -> new BackwardIterator<>(collection);

        assertThat(iterable).containsExactly(3, 2, 1);
    }

    @Test
    @DisplayName("Unmodifiable collection")
    void unmodifiableCollection() {
        Iterable<String> iterable = () -> new BackwardIterator<>(List.of("one", "two", "three"));

        assertThat(iterable).containsExactly("three", "two", "one");
    }
}
