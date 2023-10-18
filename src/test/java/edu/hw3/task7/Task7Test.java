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

        assertThat(map.containsKey(null)).isTrue();
    }

    @Test
    @DisplayName("Putting by null key twice")
    void putByNullKeyTwice() {
        Map<String, String> map = new TreeMap<>(nullFriendlyComparator());

        map.put(null, "test1");
        map.put(null, "test2");
        String get = map.get(null);

        assertThat(get).isNotNull().isEqualTo("test2");
    }

    @Test
    @DisplayName("Removing by null key")
    void removeByNullKey() {
        Map<String, String> map = new TreeMap<>(nullFriendlyComparator());

        map.put(null, "test1");
        map.remove(null);

        assertThat(map.containsKey(null)).isFalse();
    }

    @Test
    @DisplayName("Null and non-null keys")
    void nullAndNonNull() {
        Map<String, String> map = new TreeMap<>(nullFriendlyComparator());

        map.put(null, "null");
        map.put("key", "value");
        String get1 = map.get(null);
        String get2 = map.get("key");

        assertThat(get1).isNotNull().isEqualTo("null");
        assertThat(get2).isNotNull().isEqualTo("value");
    }
}
