package edu.hw6.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiskMapTest {
    @Test
    @DisplayName("Empty map")
    void emptyMap() {
        Map<String, String> map = new DiskMap();

        assertThat(map).size().isZero();
        assertThat(map).isEmpty();
        assertThat(map.entrySet()).isEmpty();
        assertThat(map.keySet()).isEmpty();
        assertThat(map.values()).isEmpty();
    }

    @Test
    @DisplayName("Put")
    void put() {
        Map<String, String> map = new DiskMap();

        String previousValue = map.put("key", "value");

        assertThat(map).containsEntry("key", "value");
        assertThat(previousValue).isNull();
    }

    @Test
    @DisplayName("Get")
    void get() {
        Map<String, String> map = new DiskMap();

        map.put("key", "value");
        String actual = map.get("key");

        assertThat(actual).isEqualTo("value");
    }

    @Test
    @DisplayName("Multiple puts by one key")
    void multiplePutsByOneKey() {
        Map<String, String> map = new DiskMap();

        map.put("key", "value1");
        String previousValue = map.put("key", "value2");

        assertThat(map)
                .containsEntry("key", "value2")
                .doesNotContainEntry("key", "value1");
        assertThat(previousValue).isEqualTo("value1");
    }

    @Test
    @DisplayName("Multiple puts by different keys")
    void multiplePutsByDifferentKeys() {
        Map<String, String> map = new DiskMap();

        map.put("key1", "value1");
        map.put("key2", "value2");

        assertThat(map)
                .containsEntry("key1", "value1")
                .containsEntry("key2", "value2");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", ".", "..", "/", "\\", " ", ":", "String with spaces and\nline breaks", "😭"})
    @DisplayName("Special characters")
    void specialCharacters(String stringWithSpecialCharacters) {
        Map<String, String> map = new DiskMap();

        map.put(stringWithSpecialCharacters, stringWithSpecialCharacters);

        assertThat(map).containsEntry(stringWithSpecialCharacters, stringWithSpecialCharacters);
    }

    @Test
    @DisplayName("Remove")
    void remove() {
        Map<String, String> map = new DiskMap();

        map.put("key1", "value1");
        map.put("key2", "value2");
        map.remove("key1");

        assertThat(map)
                .containsEntry("key2", "value2")
                .doesNotContainEntry("key1", "value1");
    }

    @Test
    @DisplayName("Put after remove")
    void putAfterRemove() {
        Map<String, String> map = new DiskMap();

        map.put("key", "value1");
        map.remove("key");
        map.put("key", "value2");

        assertThat(map).containsEntry("key", "value2");
    }

    @Test
    @DisplayName("Null key")
    void nullKey() {
        Map<String, String> map = new DiskMap();

        assertThat(map.get(null)).isNull();
        assertThat(map.remove(null)).isNull();
        assertThat(map.containsKey(null)).isFalse();
        assertThat(map.containsValue(null)).isFalse();
        assertThrows(NullPointerException.class, () -> map.put(null, "value"));
    }

    @Test
    @DisplayName("Values")
    void values() {
        Map<String, String> map = new DiskMap();

        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        Collection<String> values = map.values();

        assertThat(values).containsExactlyInAnyOrder("value1", "value2", "value3");
    }

    @Test
    @DisplayName("Put all")
    void putAll() {
        Map<String, String> map = new DiskMap();
        Map<String, String> otherMap = new HashMap<>();

        map.put("key0", "value0");
        otherMap.put("key1", "value1");
        otherMap.put("key2", "value2");
        otherMap.put("key3", "value3");
        map.putAll(otherMap);

        assertThat(map)
                .containsEntry("key0", "value0")
                .containsEntry("key1", "value1")
                .containsEntry("key2", "value2")
                .containsEntry("key3", "value3");
    }

    @Test
    @DisplayName("Multiple instances")
    void multipleInstances() {
        Map<String, String> map1 = new DiskMap();
        Map<String, String> map2 = new DiskMap();

        map1.put("key1", "value1");
        map2.put("key2", "value2");

        assertThat(map1)
                .containsEntry("key1", "value1")
                .doesNotContainEntry("key2", "value2");
        assertThat(map2)
                .containsEntry("key2", "value2")
                .doesNotContainEntry("key1", "value1");
    }

    @Test
    @DisplayName("Clear")
    void clear() {
        Map<String, String> map = new DiskMap();

        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.clear();

        assertThat(map).isEmpty();
    }
}
