package edu.hw3.task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class BackwardIterator<E> implements Iterator<E> {
    private static final String COLLECTION_CANNOT_BE_NULL = "collection cannot be null";
    private final ListIterator<E> iterator;

    public BackwardIterator(Collection<E> collection) {
        Objects.requireNonNull(collection, COLLECTION_CANNOT_BE_NULL);
        iterator = List.copyOf(collection).listIterator(collection.size());
    }

    public BackwardIterator(List<E> list) {
        Objects.requireNonNull(list, COLLECTION_CANNOT_BE_NULL);
        iterator = list.listIterator(list.size());
    }

    @Override
    public boolean hasNext() {
        return iterator.hasPrevious();
    }

    @Override
    public E next() {
        return iterator.previous();
    }
}
