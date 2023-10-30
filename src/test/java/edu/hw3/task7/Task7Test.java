package edu.hw3.task7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static edu.hw3.task7.Task7.nullFriendlyComparator;

class Task7Test {

    @Test
    @DisplayName("Putting by null key")
    void putByNullKey() {
        Map<String, String> map = new TreeMap<>(nullFriendlyComparator());

        map.put(null, "test");

        assertThat(map).containsKey(null);
    }

    @Test
    @DisplayName("Putting by null key twice")
    void putByNullKeyTwice() {
        Map<String, String> map = new TreeMap<>(nullFriendlyComparator());

        map.put(null, "test1");
        map.put(null, "test2");

        assertThat(map)
                .doesNotContainEntry(null, "test1")
                .containsEntry(null, "test2");
    }

    @Test
    @DisplayName("Removing by null key")
    void removeByNullKey() {
        Map<String, String> map = new TreeMap<>(nullFriendlyComparator());

        map.put(null, "test");
        map.remove(null);

        assertThat(map).doesNotContainEntry(null, "test");
    }

    @Test
    @DisplayName("Null and non-null keys")
    void nullAndNonNull() {
        Map<String, String> map = new TreeMap<>(nullFriendlyComparator());

        map.put(null, "null");
        map.put("key", "value");

        assertThat(map)
                .containsEntry(null, "null")
                .containsEntry("key", "value");
    }
}
