package edu.hw6.task1;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {
    private static final String DEFAULT_STORAGE_ROOT = System.getProperty("user.home");
    private static final String KEY_PREFIX = "entry-";

    private int size;
    private final Path entriesDirectory;

    private static String makeUniqueFileName() {
        return "." + UUID.randomUUID();
    }

    private Path getEntry(String fileName) {
        return entriesDirectory.resolve(KEY_PREFIX + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private String getKey(Path file) {
        String fileName = file.getFileName().toString();
        String withoutKeyPrefix = fileName.substring(KEY_PREFIX.length());
        return URLDecoder.decode(withoutKeyPrefix, StandardCharsets.UTF_8);
    }

    public DiskMap() {
        this(Paths.get(DEFAULT_STORAGE_ROOT, ".maps", makeUniqueFileName()));
    }

    public DiskMap(String entriesDirectoryPath) {
        this(Paths.get(entriesDirectoryPath));
    }

    public DiskMap(Path entriesDirectoryPath) {
        try {
            entriesDirectory = Files.createDirectories(entriesDirectoryPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (!(value instanceof String stringValue)) {
            return false;
        }
        try (Stream<Path> entries = Files.walk(entriesDirectory)) {
            return entries.map(entry -> {
                        try {
                            return Files.readString(entry);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .anyMatch(valueStored -> valueStored.equals(stringValue));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(Object key) {
        if (!(key instanceof String file)) {
            return null;
        }

        Path entry = getEntry(file);
        if (!Files.exists(entry)) {
            return null;
        }

        try {
            return Files.readString(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        Objects.requireNonNull(key, "Cannot put by null key");
        Path entry = getEntry(key);

        String previousValue;
        try {
            Files.createFile(entry);
            previousValue = null;
        } catch (FileAlreadyExistsException e) {
            try {
                previousValue = Files.readString(entry);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Files.writeString(entry, value, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        size++;
        return previousValue;
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof String fileName)) {
            return null;
        }

        Path entry = getEntry(fileName);
        if (!Files.exists(entry)) {
            return null;
        }

        String previousValue;
        try {
            previousValue = Files.readString(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Files.delete(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        size--;
        return previousValue;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> map) {
        for (Entry<? extends String, ? extends String> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> entryStream = getEntryStream()) {
            entryStream.forEach(entry -> {
                try {
                    Files.delete(entry);
                    size--;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @NotNull
    private Stream<Path> getEntryStream() {
        try {
            //noinspection resource
            return Files.walk(entriesDirectory).skip(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        try (Stream<Path> entries = getEntryStream()) {
            return entries
                    .map(this::getKey)
                    .collect(Collectors.toSet());
        }
    }

    @NotNull
    @Override
    public Collection<String> values() {
        try (Stream<Path> entries = getEntryStream()) {
            return entries.map(entry -> {
                try {
                    return Files.readString(entry);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        }
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        try (Stream<Path> entries = getEntryStream()) {
            return entries.map(entry -> {
                try {
                    return new AbstractMap.SimpleEntry<>(getKey(entry), Files.readString(entry));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toSet());
        }
    }
}
