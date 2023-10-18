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
        Iterator<String> iterator = new BackwardIterator<>(List.of());

        assertThat(iterator.hasNext()).isFalse();
        assertThrows(NoSuchElementException.class, iterator::next);
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
        Iterator<Integer> iterator = new BackwardIterator<>(collection);

        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isFalse();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("Unmodifiable collection")
    void unmodifiableCollection() {
        List<String> list = List.of("one", "two", "three");

        Iterator<String> iterator = new BackwardIterator<>(list);

        assertThat(iterator.next()).isEqualTo("three");
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("two");
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("one");
        assertThat(iterator.hasNext()).isFalse();
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
